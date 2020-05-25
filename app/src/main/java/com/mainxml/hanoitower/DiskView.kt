package com.mainxml.hanoitower

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 汉诺塔 盘子
 * @author zcp 2020/5/23
 */
class DiskView : View {

    private var cornerRadius: Float = dp2px(4).toFloat()
    private var color: Int = Color.BLACK
    private val paint = Paint()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.apply {
            val attributes = obtainStyledAttributes(attrs, R.styleable.DiskView)
            color = attributes.getColor(R.styleable.DiskView_color, color)
            cornerRadius = attributes.getDimension(R.styleable.DiskView_cornerRadius, cornerRadius)
            attributes.recycle()
        }
        init()
    }

    private fun init() {
        paint.color = color
        paint.apply {
            color = color
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultWidth = dp2px(56)
        val defaultHeight = dp2px(8)
        val measuredWidth = resolveSize(defaultWidth, widthMeasureSpec)
        val measuredHeight = resolveSize(defaultHeight, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            val l = paddingLeft.toFloat()
            val t = paddingTop.toFloat()
            val r = paddingRight.toFloat()
            val b = paddingBottom.toFloat()

            val width = width - l - r
            val height = height - t - b

            drawRoundRect(l, t, width, height, cornerRadius, cornerRadius, paint)
        }
    }

    private fun dp2px(dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}