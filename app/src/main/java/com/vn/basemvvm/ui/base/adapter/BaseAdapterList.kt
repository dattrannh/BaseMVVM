package com.vn.basemvvm.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapterList<D: Any, V: ViewDataBinding>(val layoutId: ((Int) -> Int),
                                                       val onBind: ((V, D) -> Unit)? = null) :
    RecyclerView.Adapter<BaseAdapterList.BaseViewHolder<D, V>>() {

    private var listData = mutableListOf<D>()
    var onClickedItem: ((Int, D) -> Unit)? = null

    override fun getItemCount(): Int = listData.size

    override fun getItemViewType(position: Int): Int {
        return layoutId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<D, V> {
        val inflater = LayoutInflater.from(parent.context)
        val binding: V = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BaseViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<D, V>, position: Int) {
        val item = listData[position]
        holder.onBind(item)
        onBind?.invoke(holder.dataBinding, item)
        holder.itemView.setOnClickListener {
            onClickedItem?.invoke(position, listData[position])
        }
    }

    open class BaseViewHolder<D: Any, V: ViewDataBinding>(open val dataBinding: V) : RecyclerView.ViewHolder(dataBinding.root) {

        fun onBind(data: D) {

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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        onClickedItem = null
    }
}

