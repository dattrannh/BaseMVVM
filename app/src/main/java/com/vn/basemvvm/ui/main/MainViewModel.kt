package com.vn.basemvvm.ui.main

import androidx.lifecycle.MutableLiveData
import com.vn.basemvvm.R
import com.vn.basemvvm.data.model.Post
import com.vn.basemvvm.databinding.ItemPostBinding
import com.vn.basemvvm.di.network.ApiClient
import com.vn.basemvvm.ui.base.adapter.BaseAdapterList
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val apiClient: ApiClient): BaseViewModel() {

    val count: MutableLiveData<String>  = MutableLiveData()

    val adapter = BaseAdapterList({R.layout.item_post}) { binding: ItemPostBinding, post: Post -> binding.data = post }

    fun getData() {
        composite.add(apiClient.getPosts()
            .delay(10, TimeUnit.SECONDS)
            .doOnSubscribe { showLoading() }
            .doFinally { dismissLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
//                print(result.size)
//                count.value = "Co " + result.size + " ket qua"
                adapter.setData(result)
            }, { }))
    }
}