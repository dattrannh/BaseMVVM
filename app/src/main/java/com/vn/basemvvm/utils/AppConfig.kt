package com.vn.basemvvm.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import kotlin.properties.Delegates

object AppConfig {

    lateinit var connectivityManager: ConnectivityManager
    lateinit var displayMetrics: DisplayMetrics
    var statusBarSize by Delegates.notNull<Int>()

    fun setup(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        displayMetrics = getScreen(context)
        statusBarSize = getStatusBarHeight(context)
    }

    private fun getScreen(context: Context): DisplayMetrics {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(dm)
        return dm
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) { context.resources.getDimensionPixelSize(resourceId) } else 0
    }

    fun dpToPx(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()

    fun pxToDp(px: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px.toFloat(), displayMetrics)

    fun dpToSp(px: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px.toFloat(), displayMetrics)
}