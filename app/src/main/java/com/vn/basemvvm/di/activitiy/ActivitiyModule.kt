package com.vn.basemvvm.di.activitiy

import com.vn.basemvvm.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiyModule {

//	@ActivityScope
	@ContributesAndroidInjector
	abstract fun bindMainActivity(): MainActivity

}

