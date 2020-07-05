
package com.vn.basemvvm.ui.base.activity

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType


abstract class BaseActivityBinding<T: ViewDataBinding, V: BaseViewModel> : BaseActivity() {

    open lateinit var dataBinding: T
    open lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.bind(view)!!
        dataBinding.lifecycleOwner = this
        @Suppress("UNCHECKED_CAST")
        val clazz: Class<V> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<V>
        viewModel = injectViewModel(clazz)
        viewModel.progressDialog.observe(this, Observer {
            if (it.status) showLoading(it.touchOutside, it.canGoBack)
             else dismissLoading()
        })
        viewModel.toastLiveData.observe(this,  Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        dataBinding.unbind()
        super.onDestroy()
    }
}