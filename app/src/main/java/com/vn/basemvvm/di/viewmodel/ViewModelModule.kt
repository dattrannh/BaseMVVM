package com.vn.basemvvm.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vn.basemvvm.ui.TempViewModel
import com.vn.basemvvm.ui.blank.BlankViewModel
import com.vn.basemvvm.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlankViewModel::class)
    abstract fun blankViewModel(viewModel: BlankViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TempViewModel::class)
    abstract fun tempViewModel(viewModel: TempViewModel): ViewModel

}