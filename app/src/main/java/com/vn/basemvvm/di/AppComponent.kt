package com.vn.basemvvm.di

import android.content.Context
import com.vn.basemvvm.App
import com.vn.basemvvm.di.activitiy.ActivitiyModule
import com.vn.basemvvm.di.fragment.FragmentModule
import com.vn.basemvvm.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppModules::class,
    ActivitiyModule::class,
    FragmentModule::class,
    ViewModelModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(myApp: App)
}