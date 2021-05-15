package com.cfox.esview.view

import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView

class TrackFlyView : AppCompatImageView {
    private var targetView : View? = null
    private val obj = Object()
    private var animDuration = 5000L
    private var isNeedStartAnim = false
    private var animSet:AnimatorSet ? = null
    private var inited = false
    private val targetXY = IntArray(2)
    private val trackXY = IntArray(2)

    private var listenerAdapter : AnimatorListenerAdapter ? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initParams()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initParams()
    }

    private fun initParams() {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                targetView?.let {
                    it.getLocationInWindow(targetXY)
                    this@TrackFlyView.getLocationInWindow(trackXY)
                    inited = true
                }
                if (inited) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    synchronized(obj) {
                        if (isNeedStartAnim) {
                            triggerStartAnim()
                        }
                    }
                }
            }
        })
    }

    fun setListener(listenerAdapter: AnimatorListenerAdapter) {
        this.listenerAdapter = listenerAdapter
    }

    //目标view
    fun targetView(view : View) {
        targetView = view
    }

    @SuppressLint("Range")
    fun startTrack(duration : Long) {
        visibility = View.VISIBLE
        animDuration = duration
        animSet?.cancel()
        if (inited) {
            triggerStartAnim()
        } else {
            synchronized(obj) {
                isNeedStartAnim = true
            }
        }
    }

    fun stopTrack() {
        visibility = View.GONE
        animSet?.cancel()
    }

    private fun triggerStartAnim() {
        animSet = createTranslationAnim()
        animSet?.start()
    }

    private fun createTranslationAnim() : AnimatorSet {
        val animatorSet = AnimatorSet()

        val translationX= ObjectAnimator.ofFloat(this, "translationX", 0f,targetXY[0].toFloat() - trackXY[0].toFloat())
        translationX.duration = animDuration
        translationX.interpolator = AccelerateInterpolator()

        val translationY= ObjectAnimator.ofFloat(this, "translationY",  0f, targetXY[1].toFloat() - trackXY[1].toFloat())
        translationY.duration = animDuration
        translationY.interpolator = AccelerateInterpolator()

        val alphawAnimation= ObjectAnimator.ofFloat(this, "alpha", 0.0f,1.0F, 0.4f)
        alphawAnimation.duration = animDuration
        alphawAnimation.interpolator = DecelerateInterpolator()

        animatorSet
            .play(translationX)
            .with(translationY)
            .with(alphawAnimation)

        listenerAdapter?.let {
            animatorSet.addListener(it)
        }

        return animatorSet
    }
}