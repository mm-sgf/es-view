package com.cfox.esview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView

class ShakeImageView : AppCompatImageView {

    private val ANIM_DURATION = 100L

    private var animSet:AnimatorSet ? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
    }

    fun startShake() {
        animSet?.cancel()
        animSet = createAnim()
        animSet?.start()
    }

    fun stopShake() {
        animSet?.cancel()
    }

    private fun createAnim() : AnimatorSet {
        val animatorSet = AnimatorSet()
        val scaleXAnimation= ObjectAnimator.ofFloat(this, "scaleX", 1.0F,1.2f, 1.0f)
        scaleXAnimation.duration = ANIM_DURATION
        scaleXAnimation.interpolator = LinearInterpolator()
        val scaleYAnimation= ObjectAnimator.ofFloat(this, "scaleY", 1.0F,1.2f, 1.0f)
        scaleYAnimation.duration = ANIM_DURATION
        scaleYAnimation.interpolator = LinearInterpolator()
        animatorSet
                .play(scaleXAnimation)
                .with(scaleYAnimation)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                this@ShakeImageView.scaleX = 1f
                this@ShakeImageView.scaleY = 1f
            }
        })
        return animatorSet
    }
}