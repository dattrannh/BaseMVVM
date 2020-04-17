package com.vn.basemvvm.ui.base.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment() {

    var canceledOnTouchOutside: Boolean = false
    var dim: Float = 0.5f
    private var mDialog: Dialog? = null
    protected abstract val layoutId: Int
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        mDialog = dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        val window = dialog.window
        window!!.setGravity(Gravity.CENTER)
        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setDimAmount(dim)
        dialog.setOnKeyListener{ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                return@setOnKeyListener onBackPressed()
            }
            return@setOnKeyListener false
        }
        return dialog
    }


    override fun onDetach() {
        mDialog = null
        super.onDetach()
    }

    private fun setLayout(width: Int, height: Int) {
        mDialog?.window!!.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    protected open fun init(view: View) {

    }

    public open fun onBackPressed() : Boolean {
        return true
    }
}
