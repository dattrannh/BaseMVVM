package com.vn.basemvvm.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun Context.checkPermissions(vararg permissions: String): Array<String> {
    val array = mutableListOf<String>()
    for (p in permissions) {
        if (PackageManager.PERMISSION_GRANTED != packageManager.checkPermission(p, packageName))
            array.add(p)
    }
    return array.toTypedArray()
}

fun AppCompatActivity.requestPermissions(code: Int, vararg permissions: String): Boolean {
    val ls = checkPermissions(*permissions)
    return if (ls.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, ls, code)
        false
    } else {
        true
    }
}