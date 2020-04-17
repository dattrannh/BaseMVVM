package com.vn.basemvvm.ui.base.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T: Any> (open val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    abstract fun onBind(data: T)
}