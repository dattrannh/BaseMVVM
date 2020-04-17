package com.vn.basemvvm.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import com.vn.basemvvm.ui.base.activity.BaseActivityBiding
import java.lang.reflect.ParameterizedType

abstract class BaseFragmentBinding<T: ViewDataBinding, V: BaseViewModel>: BaseFragment() {

    open lateinit var dataBinding: T
    open lateinit var viewModel: V

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        dataBinding = DataBindingUtil.bind(view)!!
        dataBinding.lifecycleOwner = this
        @Suppress("UNCHECKED_CAST")
        val clazz: Class<V> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<V>
        viewModel = injectViewModel(clazz)
        return view
    }

    open fun setShowLoading() {
        val ac = activity as? BaseActivityBiding<*, *>
            ?: return
        viewModel.progressDialog = ac.viewModel.progressDialog
    }

}