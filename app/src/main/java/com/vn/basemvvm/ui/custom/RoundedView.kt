package com.vn.basemvvm.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.vn.basemvvm.R
import com.vn.basemvvm.utils.dpToPx

class RoundedView : View, ViewOutlineProcessable {

    override val viewOutlineProcessor: ViewOutlineController =
        ViewOutlineController(this)



    constructor(context: Context) : super(context) {
        setup(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.RoundedView)
            cornerRadius = array.getDimensionPixelOffset(R.styleable.RoundedView_cornerRadius, 0)
//            borderColor = array.getColor(R.styleable.RoundedView_borderColor, Color.TRANSPARENT)
//            borderWidth = array.getDimensionPixelOffset(R.styleable.RoundedView_borderWidth, 0).toFloat()
            array.recycle()
            background = generateBackgroundWithShadow(Color.RED, cornerRadius /3f, Color.RED, dpToPx(2f), Gravity.BOTTOM)
        }
    }

//    override fun setImageResource(resId: Int) {
//        super.setImageResource(resId)
//    }
//
//    override fun setImageDrawable(drawable: Drawable?) {
//        super.setImageDrawable(drawable)
//
//    }

//    override fun draw(canvas: Canvas) {
//        viewOutlineProcessor.draw(canvas) { super.draw(it) }
//    }

    private fun generateBackgroundWithShadow(backgroundColor: Int, cornerRadius: Float, shadowColor: Int, elevation: Int,shadowGravity: Int): Drawable {
        val outerRadius = (0 until 8).map{cornerRadius }.toFloatArray()
        val shapeDrawablePadding = Rect()
        shapeDrawablePadding.left = elevation
        shapeDrawablePadding.right = elevation
        val dy: Int
        when (shadowGravity) {
            Gravity.CENTER -> {
                shapeDrawablePadding.top = elevation
                shapeDrawablePadding.bottom = elevation
                dy = 0
            }
            Gravity.TOP -> {
                shapeDrawablePadding.top = elevation * 2
                shapeDrawablePadding.bottom = elevation
                dy = -1 * elevation / 3
            }
            Gravity.BOTTOM -> {
                shapeDrawablePadding.top = elevation
                shapeDrawablePadding.bottom = elevation * 2
                dy = elevation / 3
            }
            else -> {
                shapeDrawablePadding.top = elevation
                shapeDrawablePadding.bottom = elevation * 2
                dy = elevation / 3
            }
        }
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setPadding(shapeDrawablePadding)
        shapeDrawable.paint.color = backgroundColor
        shapeDrawable.paint.setShadowLayer(
            cornerRadius / 3.toFloat(),
            0f,
            dy.toFloat(),
            Color.argb(
                102,
                Color.red(shadowColor),
                Color.green(shadowColor),
                Color.blue(shadowColor)
            )
        )
        setLayerType(LAYER_TYPE_SOFTWARE, shapeDrawable.paint)
        shapeDrawable.shape = RoundRectShape(outerRadius, null, null)
        val drawable =
            LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(0, elevation, elevation * 2, elevation, elevation * 2)
        return drawable
    }

}