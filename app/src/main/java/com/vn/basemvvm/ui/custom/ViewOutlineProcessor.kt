package com.vn.basemvvm.ui.custom

import android.graphics.*
import android.view.View
import java.lang.ref.WeakReference

open class ViewOutlineController(view: View) {

    private val mView = WeakReference(view)

    val clearPaint: Paint by lazy {
        val paint = Paint()
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint
    }

    var radius: Int = 0
        set(value) {
            field = value
//            updateOutlineProvider()
//            mView.get()?.invalidate()
        }

    var borderColor = Color.TRANSPARENT
        set(value) {
            field = value
//            borderPaint.color = value
//            mView.get()?.invalidate()

        }

    var borderWidth: Float = 0f
        set(value) {
            field = value
//            borderPaint.strokeWidth = value
//            mView.get()?.invalidate()
        }

    private var borderPaint = Paint()
    private var bitmap: Bitmap? = null


    init {
        borderPaint.isAntiAlias = true
        borderPaint.style = Paint.Style.STROKE
    }

    // endregion

    // region -> Other

    private fun updateOutlineProvider() {
//        if (0 < radius && Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
//            mView.get()?.outlineProvider = object : ViewOutlineProvider() {
//                override fun getOutline(view: View, outline: Outline) {
//                    outline.setRoundRect(0, 0, view.width, view.height, radius.toFloat())
//                }
//            }
//            mView.get()?.clipToOutline = true
//        } else {
//            mView.get()?.outlineProvider = null
//            mView.get()?.clipToOutline = false
//        }
    }


    fun draw(canvas: Canvas, defaultDraw: ((Canvas?) -> Unit)) {
        val baseCanvas = getCanvas(canvas)
        defaultDraw(canvas)

//        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            clipRoundedRect(canvas)
            canvas.drawBitmap(bitmap!!, 0.0f, 0.0f, null)
//        }
        drawBorder(canvas)
    }

    private fun getCanvas(canvas: Canvas): Canvas {
        val view = mView.get() ?: return canvas
        bitmap?.recycle()
        val width = if (view.isInEditMode) view.width else canvas.width
        val height = if (view.isInEditMode) view.height else canvas.height
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        return Canvas(bitmap!!)
    }

    private fun clipRoundedRect(canvas: Canvas) {
        val path = Path()
        val rect = RectF(0.0f, 0.0f, canvas.width.toFloat(), canvas.height.toFloat())
        val direction = Path.Direction.CW
        path.fillType = Path.FillType.INVERSE_EVEN_ODD
        path.addRoundRect(rect, radius.toFloat(), radius.toFloat(), direction)
        canvas.drawPath(path, clearPaint)
    }

    private fun drawBorder(canvas: Canvas) {
        if (borderWidth != 0f && borderColor != Color.TRANSPARENT) {
            val borderWidth = borderWidth
            val halfWidth: Float = borderWidth.toFloat() / 2
            val rect = RectF(halfWidth, halfWidth, (canvas.width - halfWidth), (canvas.height - halfWidth))
            val rx = if (0 < radius) radius.toFloat() else 0.0f
            val ry = if (0 < radius) radius.toFloat() else 0.0f
            canvas.drawRoundRect(rect, rx, ry, borderPaint)
        }
    }

    // endregion
}

interface ViewOutlineProcessable {
    val viewOutlineProcessor: ViewOutlineController
    var cornerRadius: Int
        get() = viewOutlineProcessor.radius
        set(value) {
            viewOutlineProcessor.radius = value
        }
    var borderColor: Int?
        get() = viewOutlineProcessor.borderColor
        set(value) {
            viewOutlineProcessor.borderColor = value ?: Color.TRANSPARENT
        }
    var borderWidth: Float?
        get() = viewOutlineProcessor.borderWidth
        set(value) {
            viewOutlineProcessor.borderWidth = value ?: 0f
        }
}