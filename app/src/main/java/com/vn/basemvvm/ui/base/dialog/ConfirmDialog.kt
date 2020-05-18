package com.vn.basemvvm.ui.base.dialog

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.vn.basemvvm.extension.isHidden
import com.vn.basemvvm.utils.AppConfig
import kotlinx.android.synthetic.main.dialog_confirm.view.*

class ConfirmDialog: BaseDialog() {

    var callback: (() -> Unit)? = null
    var textTitle: String? = null
    var textContent: String? = null

    override val layoutId: Int = com.vn.basemvvm.R.layout.dialog_confirm

    override fun init(view: View) {
        super.init(view)
        view.dialogConfirmBtnYes.setOnClickListener {
            callback?.invoke()
            dismiss()
        }
        view.dialogConfirmBtnNo.setOnClickListener { dismiss() }
        view.dialogConfirmBtnNo.isHidden = callback == null
        view.dialogConfirmTitle.text = textTitle
        view.dialogConfirmTitle.isHidden = textTitle == null
        view.dialogConfirmContent.text = textContent
    }

    override fun onStart() {
        super.onStart()
        setLayout((AppConfig.displayMetrics.widthPixels * 0.7).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        fun show(fragment: FragmentManager, title: String? = null, message: String? = null, callback: (() -> Unit)? = null) {
            val confirmDialog = ConfirmDialog()
            confirmDialog.callback = callback
            confirmDialog.textTitle = title
            confirmDialog.textContent = message
            confirmDialog.show(fragment, ConfirmDialog::class.simpleName)
        }
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

}