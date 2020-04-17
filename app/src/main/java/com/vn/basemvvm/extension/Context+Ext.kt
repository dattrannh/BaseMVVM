package com.vn.basemvvm.extension

import android.content.Context
import com.vn.basemvvm.utils.ScreenUtils

var Context.statusBarHeight: Int
    get()  {
        return ScreenUtils.getStatusBarHeight(this)
    }
    set(value) {}