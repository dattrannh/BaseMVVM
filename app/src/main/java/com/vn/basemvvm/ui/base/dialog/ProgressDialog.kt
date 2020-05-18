package com.vn.basemvvm.ui.base.dialog

import android.content.DialogInterface
import android.view.View
import com.vn.basemvvm.R
import com.vn.basemvvm.ui.base.dialog.BaseDialog
import com.vn.basemvvm.utils.AppConfig

class ProgressDialog : BaseDialog() {

    var canGoBack = false

    override val layoutId: Int = R.layout.progress_bar


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismiss()
    }

    override fun onBackPressed(): Boolean {
        return !canGoBack
    }

    data class Configure(val status: Boolean, val touchOutside: Boolean, val canGoBack: Boolean)

}
