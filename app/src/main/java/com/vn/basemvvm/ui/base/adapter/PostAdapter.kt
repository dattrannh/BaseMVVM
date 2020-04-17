package com.vn.basemvvm.ui.base.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import com.vn.basemvvm.BR
import com.vn.basemvvm.R
import com.vn.basemvvm.data.model.Post
import com.vn.basemvvm.databinding.ItemPostBinding

class PostAdapter: BaseAdapterList<Post, PostAdapter.PostViewHolder>() {

    override fun layoutId(): Int = R.layout.item_post

    class PostViewHolder(override val dataBinding: ItemPostBinding) : BaseViewHolder<Post>(dataBinding) {

        override fun onBind(data: Post) {

//            dataBinding.setVariable(BR.data, data)
//            dataBinding.executePendingBindings()
            dataBinding.data = data
        }
    }
}

//class ViewT: LinearLayout {
//
//
//    constructor(context: Context?) : super(context)
//    constructor(context: Int?): super(null)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//
//    }
//
//    constructor(
//        context: Context?,
//        attrs: AttributeSet?,
//        defStyleAttr: Int,
//        defStyleRes: Int
//    ) : super(context, attrs, defStyleAttr, defStyleRes)
//
//
//}