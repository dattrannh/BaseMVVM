package com.vn.basemvvm.ui.temp

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.vn.basemvvm.R
import com.vn.basemvvm.ui.base.adapter.BasePagerAdapter

class TempPagerAdapter: BasePagerAdapter<TempPagerAdapter.ViewHolder>() {

    override fun getCount(): Int {
        return 10
    }

    class ViewHolder(itemView: View) : BasePagerAdapter.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(container: ViewGroup): ViewHolder {
        val imageView = ImageView(container.context)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.setImageResource(R.drawable.bay)
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}