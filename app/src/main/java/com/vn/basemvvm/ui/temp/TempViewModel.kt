package com.vn.basemvvm.ui.temp

import android.util.Log
import com.vn.basemvvm.data.db.User
import com.vn.basemvvm.di.storage.database.AppDatabase
import com.vn.basemvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random


class TempViewModel @Inject constructor(private val database: AppDatabase): BaseViewModel() {

    fun insert() {
        val id = Random.nextInt(1000)
        val user = User(id, "danny", "tran")
        composite.add(database.userDao().insert(user)
            .delay(2, TimeUnit.SECONDS)
            .doOnSubscribe { showLoading() }
            .doFinally { dismissLoading() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                showToast("Đã  lưu thành công $id")
            }, {error ->
                showToast("Lưu bị lỗi $id")
            })
        )
    }
}