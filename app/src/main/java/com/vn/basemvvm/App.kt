package com.vn.basemvvm

import android.app.Application
import com.vn.basemvvm.di.AppComponent
import com.vn.basemvvm.di.DaggerAppComponent
import com.vn.basemvvm.utils.AppConfig
import com.vn.basemvvm.utils.network.NetworkUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        AppConfig.setup(applicationContext)
        component = DaggerAppComponent.factory().create(applicationContext)
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}