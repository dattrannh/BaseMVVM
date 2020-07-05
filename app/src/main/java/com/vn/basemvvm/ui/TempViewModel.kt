package com.vn.basemvvm.ui

import android.util.Log
import com.vn.basemvvm.data.db.User
import com.vn.basemvvm.di.storage.database.AppDatabase
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


class TempViewModel @Inject constructor(private val database: AppDatabase): BaseViewModel() {

    fun insert() {
        val user = User(20, "danny", "tran")
        composite.add(database.userDao().insert(user)
            .doOnSubscribe { showLoading() }
            .doFinally { dismissLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("myInfo saved user")
            }, {error ->
                Timber.d(error)
            })
        )
    }
}