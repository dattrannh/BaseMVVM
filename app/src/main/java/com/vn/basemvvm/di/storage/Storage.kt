package com.vn.basemvvm.di.storage

import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.di.storage.frefs.SharedPreferencesStorage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface Storage {

    @Binds
    fun localStorage(pre: SharedPreferencesStorage) : LocalStorage
}