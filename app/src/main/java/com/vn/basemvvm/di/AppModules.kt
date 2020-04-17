package com.vn.basemvvm.di

import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.di.storage.frefs.PreferenceInfo
import com.vn.basemvvm.di.storage.frefs.SharedPreferencesStorage
import com.vn.basemvvm.di.network.NetworkModule
import com.vn.basemvvm.di.storage.Storage
import com.vn.basemvvm.di.viewmodel.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, Storage::class])
class AppModules {


    // Configurations

    @Singleton
    @Provides
    @PreferenceInfo
    fun filePreferences(): String {
        return "config"
    }

}