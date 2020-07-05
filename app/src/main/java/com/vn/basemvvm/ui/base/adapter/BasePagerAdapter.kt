package com.vn.basemvvm.ui.base.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import java.util.*

abstract class BasePagerAdapter<VH : BasePagerAdapter.ViewHolder?> : PagerAdapter() {
    
    private val cache: Queue<VH> = LinkedList()
    private val attached = SparseArray<VH>()
    abstract fun onCreateViewHolder(container: ViewGroup): VH
    abstract fun onBindViewHolder(holder: VH, position: Int)
    fun onRecycleViewHolder(holder: VH) {}
    
    fun getViewHolder(position: Int): VH {
        return attached[position]
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var holder = cache.poll()
        if (holder == null) {
            holder = onCreateViewHolder(container)
        }
        attached.put(position, holder)
        container.addView(holder!!.itemView, null)
        onBindViewHolder(holder, position)
        return holder
    }

    @Suppress("UNCHECKED_CAST")
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val holder = obj as VH
        attached.remove(position)
        container.removeView(holder!!.itemView)
        cache.offer(holder)
        onRecycleViewHolder(holder)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        val holder = obj as ViewHolder
        return holder.itemView === view
    }

    override fun getItemPosition(obj: Any): Int {
        return POSITION_NONE
    }

    open class ViewHolder(val itemView: View)
}