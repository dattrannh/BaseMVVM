package com.vn.basemvvm.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.vn.basemvvm.R
import com.vn.basemvvm.utils.dp2Px
import com.vn.basemvvm.utils.dpToPx

class IndicatorView: View {

    private val paintStroke = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintCircle = Paint(Paint.ANTI_ALIAS_FLAG)

    var count = 5
    var currentPosition = 0
    var progress: Float = 0f

    private var radius: Float = dp2Px(10f)
    private var spacing: Float = dp2Px(5f)
    private var strokeWidth: Float = dp2Px(1f)

    private var strokeColor = Color.BLACK
    private var selectedColor =  Color.BLUE

    private var isStyleStroke = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        if (attrs != null)  {
            val array = context.obtainStyledAttributes(attrs,  R.styleable.IndicatorView)
            radius = array.getDimensionPixelOffset(R.styleable.IndicatorView_radius, dpToPx(5f)).toFloat()
            spacing = array.getDimensionPixelOffset(R.styleable.IndicatorView_spacing, dpToPx(3f)).toFloat()
            strokeWidth = array.getDimensionPixelOffset(R.styleable.IndicatorView_strokeWidth, dpToPx(1f)).toFloat()
            strokeColor = array.getColor(R.styleable.IndicatorView_color, Color.BLACK)
            selectedColor = array.getColor(R.styleable.IndicatorView_selectedColor, Color.BLUE)
            isStyleStroke = array.getBoolean(R.styleable.IndicatorView_styleStroke, true)
            array.recycle()
        }
        paintStroke.color = strokeColor
        paintStroke.strokeWidth = strokeWidth
        paintStroke.style = if (isStyleStroke) Paint.Style.STROKE else Paint.Style.FILL
        paintStroke.isDither = true
        paintCircle.color = selectedColor
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