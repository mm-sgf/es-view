package com.cfox.esview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.cfox.es_view.R

class GradientTextView : AppCompatTextView {

    var startColor : Int = -1
    var endColor : Int = -1
    var colorDirection : Int = 0


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initStyle(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initStyle(context, attrs)
    }

    private fun initStyle(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.GradientTextView)
            startColor = a.getColor(R.styleable.GradientTextView_start_color, -1)
            endColor = a.getColor(R.styleable.GradientTextView_end_color, -1)
            colorDirection = a.getInt(R.styleable.GradientTextView_color_direction, 0)
            a.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            setTextViewStyles(this)
        }
    }

    private fun setTextViewStyles(textView : TextView) {
        if (startColor != -1 && endColor != -1) {
            var gl : LinearGradient ? = null
            val colors = intArrayOf( startColor, endColor )//颜色的数组
            val position = floatArrayOf( 0f, 1.0f )//颜色渐变位置的数组
            gl = when(colorDirection) {
                1 -> LinearGradient(0f, textView.height.toFloat(), 0f, 0f, colors, position, Shader.TileMode.CLAMP)
                2 -> LinearGradient(0f, 0f, textView.width.toFloat(), 0f, colors, position, Shader.TileMode.CLAMP)
                3 -> LinearGradient(textView.width.toFloat(), 0f, 0f, 0f, colors, position, Shader.TileMode.CLAMP)
                else -> LinearGradient(0f, 0f, 0f, textView.height.toFloat(), colors, position, Shader.TileMode.CLAMP)
            }

            textView.paint.shader = gl
            textView.invalidate()
        }
    }

}