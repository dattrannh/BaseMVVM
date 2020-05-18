package com.vn.basemvvm.ui.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.DialogFragment
import com.vn.basemvvm.utils.AppConfig

abstract class BaseDialog : DialogFragment() {

    var canceledOnTouchOutside: Boolean = false
    var dim: Float = 0.4f
    private var mDialog: Dialog? = null
    protected abstract val layoutId: Int
    private lateinit var mView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        mDialog = dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mView = dialog.layoutInflater.inflate(layoutId, null)
        dialog.setContentView(mView)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setDimAmount(dim)
        dialog.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                return@setOnKeyListener onBackPressed()
            }
            return@setOnKeyListener false
        }
        init(mView)
        return dialog
    }

    override fun onDetach() {
        mDialog = null
        super.onDetach()
    }

    fun setLayout(width: Int, height: Int) {
        mDialog?.window!!.setLayout(width, height)
    }


    protected open fun init(view: View) {

    }

    public open fun onBackPressed() : Boolean {
        return true
    }
}
