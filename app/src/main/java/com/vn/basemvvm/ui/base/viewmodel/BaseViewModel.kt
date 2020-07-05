package com.vn.basemvvm.ui.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vn.basemvvm.ui.base.dialog.ProgressDialog
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel: ViewModel() {

    open val composite = CompositeDisposable()
    open var progressDialog = MutableLiveData<ProgressDialog.Configure>()
    open var toastLiveData = MutableLiveData<String>()

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }

    fun showLoading(touchOutside: Boolean = false, canGoBack: Boolean = false) {
        progressDialog.postValue(
            ProgressDialog.Configure(
            status = true,
            touchOutside = touchOutside,
            canGoBack = canGoBack
        ))
    }

    fun dismissLoading() {
        progressDialog.postValue(
            ProgressDialog.Configure(
            status = false,
            touchOutside = false,
            canGoBack = false
        ))
    }

    fun showToast(string: String) {
        toastLiveData.postValue(string)
    }

}