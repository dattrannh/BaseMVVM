package com.vn.basemvvm.extension

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vn.basemvvm.utils.dpToPx

fun <T : View> Activity.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { findViewById<T>(idRes) }
}

fun <T : View> Fragment.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { view!!.findViewById<T>(idRes) }
}

fun <T : View> View.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { findViewById<T>(idRes) }
}

fun <T : View> RecyclerView.ViewHolder.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { itemView.findViewById<T>(idRes) }
}

private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)


fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.apply { leftMargin = dpToPx(this) }
        top?.apply { topMargin = dpToPx(this) }
        right?.apply { rightMargin = dpToPx(this) }
        bottom?.apply { bottomMargin = dpToPx(this) }
    }
}

var View.isHidden: Boolean
    get() {
        return visibility == View.GONE
    }
    set(value) {
        if (visibility == View.VISIBLE && value) {
            visibility = View.GONE
        } else if (visibility == View.GONE && !value) {
            visibility = View.VISIBLE
        } else {

        }
    }

var View.isHiddenAnimate: Boolean
    get() {
        return visibility == View.GONE
    }
    set(value) {
        if (visibility == View.VISIBLE && value) {
            animate().cancel()
            animate().alpha(0f).setDuration(300).withEndAction {
                visibility = View.GONE
            }.start()
        } else if (visibility == View.GONE && !value) {
            animate().cancel()
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(300).start()
        } else {

        }
    }

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

