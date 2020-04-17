package com.vn.basemvvm.ui.base.dialog

import android.view.View
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_confirm.view.*

class ConfirmDialog: BaseDialog() {

    var callback: (() -> Unit)? = null
    override val layoutId: Int = com.vn.basemvvm.R.layout.dialog_confirm

    override fun init(view: View) {
        super.init(view)
        view.yes.setOnClickListener {
            callback?.invoke()
            dismiss()
        }
        view.no.setOnClickListener { dismiss() }
    }

    companion object {
        fun show(fragment: FragmentManager, message: String? = null, callback: (() -> Unit)? = null ) {
            val confirmDialog = ConfirmDialog()
            confirmDialog.callback = callback
            confirmDialog.show(fragment, ConfirmDialog::class.simpleName)
        }
    }

}