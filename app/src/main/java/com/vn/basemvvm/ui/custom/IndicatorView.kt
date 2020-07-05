package com.vn.basemvvm.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.vn.basemvvm.utils.dp2Px

class IndicatorView: View {

    val paintStroke = Paint(Paint.ANTI_ALIAS_FLAG)
    val paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)

    var count = 5
    var radius: Float = dp2Px(10f)
    var spacing: Float = dp2Px(5f)
    var currentPosition = 0
    var progress: Float = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(attrs)
    }

    fun setup(attrs: AttributeSet?) {
        paintStroke.color = Color.BLACK
        paintStroke.strokeWidth = dp2Px(1f)
            paintStroke.style = Paint.Style.STROKE
        paintStroke.isDither = true
        paintCircle.color = Color.BLACK
        paintCircle.style = Paint.Style.FILL
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val widthCircles = radius * 2 * count + spacing * (count - 1)
        var x = (width - widthCircles) / 2 - radius
        val y = height / 2f
        for (i in 0 until count) {
            canvas.drawCircle(x + i * (radius * 3 + spacing), y, radius, paintStroke)
        }
        x += currentPosition * (radius * 3 + spacing)
        x += (radius * 3 + spacing) * progress
        canvas.drawCircle(x, y, radius, paintCircle)
    }

    fun update(count: Int, pos: Int, progress: Float) {
        this.count = count
        currentPosition = pos
        this.progress = progress
        invalidate()
    }
}