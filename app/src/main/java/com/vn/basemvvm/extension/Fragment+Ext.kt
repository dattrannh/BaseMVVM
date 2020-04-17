package com.vn.basemvvm.extension

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.vn.basemvvm.R
import com.vn.basemvvm.ui.base.activity.BaseActivity
import kotlin.reflect.KClass

fun Fragment.pushFragment(clazz: KClass<out Fragment>, bundle: Bundle? = null, animate: Boolean = true) {
    val con = context ?: return
    val activity = con as? BaseActivity ?: return
    activity.pushFragment(clazz = clazz, bundle = bundle, animate = animate)
}

fun Fragment.popFragment(clazz: KClass<out Fragment>? = null, animate: Boolean = true) : Boolean {
    val activity = context as? BaseActivity ?: return false
    return activity.popFragment(clazz = clazz, animate = animate)
}
fun Fragment.popToRoot(animate: Boolean = true) {
    val activity = context as? BaseActivity ?: return
    return activity.popToRoot(animate = animate)
}

fun Fragment.transitionInLeft() {
    val con = context
    if (con != null) {
        val anim = AnimationUtils.loadAnimation(con, R.anim.slide_in_left)
        view?.startAnimation(anim)
    }
}

fun Fragment.transitionOutLeft() {
    val con = context
    if (con != null) {
        val anim = AnimationUtils.loadAnimation(con, R.anim.slide_out_left)
        view?.startAnimation(anim)
    }
}

fun Fragment.showLoading(touchOutside: Boolean = false, canGoBack: Boolean = true) {
    (activity as? BaseActivity)?.showLoading(touchOutside = touchOutside, canGoBack = canGoBack)
}

fun Fragment.dismissLoading() {
    (activity as? BaseActivity)?.dismissLoading()
}