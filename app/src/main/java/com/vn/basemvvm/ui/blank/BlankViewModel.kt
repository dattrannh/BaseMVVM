package com.vn.basemvvm.ui.blank

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vn.basemvvm.data.model.Post
import com.vn.basemvvm.di.network.ApiClient
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BlankViewModel @Inject constructor(private val apiClient: ApiClient): BaseViewModel() {

    val listData: MutableLiveData<List<Post>> = MutableLiveData()

    fun getData() {
        composite.add(Observable.timer(1, TimeUnit.SECONDS)
            .concatMap { apiClient.getPosts()}
            .doOnSubscribe { showLoading() }
            .doOnTerminate { dismissLoading() }
            .subscribe ({ onNext ->
                listData.postValue(onNext)
                Log.d("myInfo",  "onNext " + onNext.size)
            },{}
        ))

//        composite.add(apiClient.getPosts()
//            .delay(4, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { showLoading() }
//            .doOnTerminate { dismissLoading() }
//            .subscribe ({ onNext ->
//                listData.postValue(onNext)
//                Log.d("myInfo",  "onNext " + onNext.size)
//            },{})
//        )
    }
}