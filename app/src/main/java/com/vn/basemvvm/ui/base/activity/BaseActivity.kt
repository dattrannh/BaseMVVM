package com.vn.basemvvm.ui.base.activity;

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vn.basemvvm.R
import com.vn.basemvvm.data.model.ErrorResponse
import com.vn.basemvvm.extension.transitionInLeft
import com.vn.basemvvm.extension.transitionOutLeft
import com.vn.basemvvm.ui.base.dialog.ConfirmDialog
import com.vn.basemvvm.ui.base.dialog.ProgressDialog
import com.vn.basemvvm.utils.network.NetworkUtils
import com.vn.basemvvm.utils.rx.RxBus
import com.vn.basemvvm.utils.rx.RxBus.STATUS_CODE
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject
import kotlin.math.hypot
import kotlin.reflect.KClass

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    protected lateinit var mView: View
    private var listTagFragments = ArrayDeque<String>()
    private val mHandler = Handler(Looper.getMainLooper())
    var delayMillis = 500L
    private var progressDialog: ProgressDialog? = null
    private var isLoading = false
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        mView = layoutInflater.inflate(layoutId, null)
        setContentView(mView)
        mHandler.postDelayed({
            initDelayed()
        }, delayMillis)
    }

    open fun initDelayed() {

    }

    override fun onStart() {
        super.onStart()
        RxBus.subscribe(name = STATUS_CODE, clazz = ErrorResponse::class) { error ->
            val statusCode = error.code
            if (statusCode == -1) {
                val string = if (!NetworkUtils.isNetworkConnected())
                    getString(R.string.no_internet)
                 else getString(R.string.unknown_internet)
//                Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
                ConfirmDialog.show(supportFragmentManager, message = string)
            } else {
                handleResponse(statusCode, error.cause)
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onStop() {
        super.onStop()
        RxBus.unregister(name = STATUS_CODE)
    }

    override fun onBackPressed() {
        if (!popFragment()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        progressDialog = null
        super.onDestroy()
    }

    open fun handleResponse(code: Int, cause: Any?) {
        Toast.makeText(applicationContext, "Error status = ${"" + code + " ; cause = " + cause?.toString()}", Toast.LENGTH_SHORT).show()
    }

    //region view model

    inline fun <reified V : ViewModel> injectViewModel(): V {
        return ViewModelProvider(this, factory)[V::class.java]
    }

    fun <V : ViewModel> injectViewModel(clazz: Class<V>): V {
        return ViewModelProvider(this, factory)[clazz]
    }

    //endregion

    //region Fragment

    open fun popToRoot(animate: Boolean = true) {
        val first = listTagFragments.poll()
        var last = listTagFragments.poll()
        val supportFm = supportFragmentManager
        while (last != null) {
            val fragment = supportFm.findFragmentByTag(last)
            if (fragment != null) {
                popFm(fragment, false)
            }
            last = listTagFragments.poll()
        }
        popFragment(tag = first, animate = animate)
    }

    open fun popFragment(clazz: KClass<out Fragment>? = null, tag: String? = null, animate: Boolean = true) : Boolean {
        var fragment: Fragment? = null
        if (clazz != null) {
            fragment = supportFragmentManager.fragments.first { it.javaClass.simpleName == clazz.simpleName }
        }
        if (fragment == null) {
            fragment = supportFragmentManager.findFragmentByTag(tag ?: listTagFragments.peek()) ?: return false
        }
        listTagFragments.poll()
        supportFragmentManager.findFragmentByTag(tag ?: listTagFragments.peek())?.transitionInLeft()
        popFm(fragment, animate)
        return true
    }

    private fun popFm(fragment: Fragment, animate: Boolean) {
        val trans = supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
        if (animate) {
            trans.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        trans.remove(fragment).commitNow()
    }

    open fun pushFragment(clazz: KClass<out Fragment>, bundle: Bundle? = null, tag: String? = null, animate: Boolean = true) {
        val fragment = clazz.java.newInstance()
        fragment.arguments = bundle
        val mTag = tag ?: clazz.java.simpleName  + "${System.nanoTime()}"
        supportFragmentManager.findFragmentByTag(listTagFragments.peek())?.transitionOutLeft()
        val trans = supportFragmentManager
            .beginTransaction()
        if (animate) {
            trans.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
        }
        trans.disallowAddToBackStack()
            .add(android.R.id.content, fragment, mTag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commitNow()
        listTagFragments.push(mTag)
    }

    //endregion

    //region Progress bar

    fun showLoading(touchOutside: Boolean, canGoBack: Boolean) {
        if (isLoading) {
            return
        }
        isLoading = true
        val dialog = progressDialog ?: ProgressDialog()
        progressDialog = dialog
        if (!dialog.isAdded) {
            dialog.canceledOnTouchOutside = touchOutside
            dialog.canGoBack = canGoBack
            dialog.show(supportFragmentManager, ProgressDialog::class.java.simpleName)
        }
    }

    fun showLoading() {
        this.showLoading(touchOutside = false, canGoBack = true)
    }

    fun dismissLoading() {
        if (!isLoading) {
            return
        }
        isLoading = false
        progressDialog?.dismiss()
    }

    //endregion

    //region touch screen to hidden keyboard

    private var focusedViewOnActionDown: View? = null
    private var touchWasInsideFocusedView = false
    private  var hasMove = false
    private var rawX = 0f
    private  var rawY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                rawX = ev.rawX
                rawY = ev.rawY
                hasMove = false
                focusedViewOnActionDown = currentFocus
                if (focusedViewOnActionDown != null) {
                    val rect = Rect()
                    val coordinates = IntArray(2)
                    focusedViewOnActionDown!!.getLocationOnScreen(coordinates)
                    rect[coordinates[0], coordinates[1], coordinates[0] + focusedViewOnActionDown!!.width] =
                        coordinates[1] + focusedViewOnActionDown!!.height
                    val x = ev.x.toInt()
                    val y = ev.y.toInt()
                    touchWasInsideFocusedView = rect.contains(x, y)
                }
            }
            MotionEvent.ACTION_MOVE -> if (!hasMove) {
                val delta = hypot(rawX - ev.rawX.toDouble(), rawY - ev.rawY.toDouble()).toFloat()
                hasMove = delta > 6f
            }
            MotionEvent.ACTION_UP -> if (focusedViewOnActionDown != null) {
                val consumed = super.dispatchTouchEvent(ev)
                val currentFocus = currentFocus
                if (hasMove) {
                    return consumed
                }
                if (currentFocus == focusedViewOnActionDown) {
                    if (touchWasInsideFocusedView) {
                        return consumed
                    }
                } else if (currentFocus is EditText) {
                    return consumed
                }
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    focusedViewOnActionDown!!.windowToken,
                    0
                )
                return consumed
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    //endregion
}
