package com.vn.basemvvm.di.fragment

import com.vn.basemvvm.ui.blank.BlankFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

	@ContributesAndroidInjector
	abstract fun blankFragment(): BlankFragment
}