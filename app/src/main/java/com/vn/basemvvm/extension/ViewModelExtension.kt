package com.vn.basemvvm.extension

//inline fun <reified T : ViewModel> BaseActivity.injectViewModel(factory: ViewModelProvider.Factory): T {
//    val viewModel = ViewModelProvider(this, factory)[T::class.java]
//    if (viewModel is BaseViewModel) {
//        viewModel.setLoading(this)
//    }
//    return viewModel
//}
//
//inline fun <reified T : ViewModel> BaseFragment.injectViewModel(factory: ViewModelProvider.Factory): T {
//    val viewModel = ViewModelProvider(this, factory)[T::class.java]
//    if (viewModel is BaseViewModel) {
//        val activity = getActivity() as? BaseActivity
//        if (activity != null)
//          viewModel.setLoading(activity)
//    }
//    return viewModel
//}
