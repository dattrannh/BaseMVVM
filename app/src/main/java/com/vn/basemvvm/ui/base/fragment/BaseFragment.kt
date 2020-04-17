package com.vn.basemvvm.ui.base.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    protected var mHandler = Handler()
    protected var durationDelayed: Long = 200

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    abstract val layoutId: Int


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDetach() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        postponeEnterTransition()
//        println(savedInstanceState == null)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        view.isClickable = true
        view.isFocusable = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHandler.postDelayed({ this.initDelayed() }, durationDelayed)
        init(view)
    }


    inline fun <reified T : ViewModel> injectViewModel(): T {
        return ViewModelProvider(this, factory)[T::class.java]
    }

    fun <V : ViewModel> injectViewModel(clazz: Class<V>): V {
        return ViewModelProvider(this, factory)[clazz]
    }


//    override fun onStart() {
//        super.onStart()
//        val animation = AnimationUtils.loadAnimation(context!!, R.anim.slide_in_right)
//        animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {
//
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//
//            }
//
//            override fun onAnimationStart(animation: Animation?) {
//                view?.visibility = View.VISIBLE
//            }
//
//        })
//        view?.startAnimation(animation)
//    }
    protected open fun init(view: View) {

    }

    protected open fun initDelayed() {

    }

    open fun onBackPressed() : Boolean {
        return true
    }
}
