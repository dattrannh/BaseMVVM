package com.vn.basemvvm.ui.blank

import android.util.Log
import com.vn.basemvvm.di.storage.frefs.SharedPreferencesStorage
import javax.inject.Inject

class TempInject @Inject constructor(private val share: SharedPreferencesStorage) {

    fun print() {
        share.putString("chao", "ffadsfadsfa")
        Log.d("myInfo", javaClass.simpleName)
    }
}
