package com.vn.basemvvm.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.widget.ImageViewCompat
import com.vn.basemvvm.ui.custom.RoundedImageView
import com.vn.basemvvm.utils.AppConfig
import com.vn.basemvvm.utils.dpToSp
import kotlin.math.min

class RoundedImageView : AppCompatImageView {

    val paintText: Paint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.textSize = dpToSp(16)
        paint
    }

    val paintBG: Paint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint
    }

    val paintMask: Paint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE
        paint
    }





    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
    }

    override fun setBackground(background: Drawable?) {
        super.setBackground(background)
    }

    override fun setBackgroundResource(resId: Int) {
        super.setBackgroundResource(resId)
    }
    //
//    override fun draw(canvas: Canvas) {
//        super.draw(canvas)
    override fun onDraw(canvas: Canvas) {
        try {
            val drawable = drawable  ?: return
            val w = width
            val h = height
            if (w <= 0 || h <= 0) {
                return
            }
            val bitmap: Bitmap
            when (drawable) {
                is BitmapDrawable -> {
                    val b = drawable.bitmap
                    bitmap = b.copy(Bitmap.Config.ARGB_8888, true)
                }
                is ColorDrawable -> {
                    bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    val c = Canvas(bitmap)
                    c.drawColor(drawable.color)
                }
                else -> {
                    return
                }
            }
            val roundBitmap = getRoundedCroppedBitmap(bitmap, min(w, h))
            canvas.drawBitmap(roundBitmap, 0f, 0f, null)
        } catch (e: Exception) {
            Log.e(TAG, "onDraw Exception", e)
        }
    }

    private fun getRoundedCroppedBitmap(bitmap: Bitmap, radius: Int): Bitmap {
        val finalBitmap: Bitmap = if (bitmap.width != radius || bitmap.height != radius) {
            Bitmap.createScaledBitmap(bitmap, radius, radius, false)
        } else {
            bitmap
        }
        val output = Bitmap.createBitmap(finalBitmap.width, finalBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(
            0, 0, finalBitmap.width,
            finalBitmap.height
        )
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
//        canvas.drawARGB(0, 0, 0, 0)
//        paint.color = Color.parseColor("#BAB399")
        canvas.drawCircle(
            finalBitmap.width / 2.toFloat(),
            finalBitmap.height / 2.toFloat(),
            finalBitmap.width / 2.toFloat(), paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(finalBitmap, rect, rect, paint)
        return output
    }

    companion object {
        private val TAG = RoundedImageView::class.java.simpleName
    }

    fun paintText(canvas: Canvas) {
        val text = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasT = Canvas(text)
        canvasT.drawText("chao ban", 0f, height / 2f, paintText)

        canvas.drawBitmap(text, 0f, 0f, null)
        val bitmapBg = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )
        canvas.drawBitmap(bitmapBg, 30f, 0f, paintBG)


        val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(bitmapOut)

        canvas2.drawBitmap(text, 0f, 0f, null)

        val bitmapMask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasMask = Canvas(bitmapMask)

        canvasMask.drawRoundRect(
            RectF(0f, 0f, width.toFloat(), height.toFloat()),
            0f,
            0f,
            paintMask
        )
        paintMask.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        canvas2.drawBitmap(bitmapMask, 30f, 0f, paintMask)

        canvas.drawBitmap(bitmapOut, 0f, 0f, null)
    }
}