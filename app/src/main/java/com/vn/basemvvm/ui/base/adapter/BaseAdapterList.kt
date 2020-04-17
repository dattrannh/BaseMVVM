package com.vn.basemvvm.ui.base.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.vn.basemvvm.data.model.Post
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseAdapterList<D: Any, T: BaseViewHolder<D>>: RecyclerView.Adapter<T>() {

    private var listData = mutableListOf<D>()
    var onClickedItem: ((Int, D) -> Unit)? = null

    override fun getItemCount(): Int = listData.size

    abstract fun layoutId(): Int

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val inflate = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflate, layoutId(), parent, false)
        val superclass = javaClass.genericSuperclass!!
        val parameterized = superclass as ParameterizedType
        val type = parameterized.actualTypeArguments[1]
        val con = (type as Class<T>).constructors.first()
        return con.newInstance(binding) as T
    }
    override fun onBindViewHolder(holder: T, position: Int) {
        holder.onBind(data = listData[position])
        holder.itemView.setOnClickListener {
            onClickedItem?.invoke(position, listData[position])
        }
    }

    fun setData(data: List<D>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(vararg data: D) {
        listData.addAll(data)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        listData.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeAll() {
        listData.clear()
        notifyDataSetChanged()
    }
}