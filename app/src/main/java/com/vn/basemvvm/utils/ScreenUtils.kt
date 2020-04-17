package com.vn.basemvvm.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {

    fun getScreen(context: Context): DisplayMetrics {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(dm)
        return dm
    }

    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) { context.resources.getDimensionPixelSize(resourceId) } else 0
    }
}