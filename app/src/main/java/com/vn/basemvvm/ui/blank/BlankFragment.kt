package com.vn.basemvvm.ui.blank

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.vn.basemvvm.R
import com.vn.basemvvm.databinding.BlankFragmentBinding
import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.extension.bind
import com.vn.basemvvm.extension.pushFragment
import com.vn.basemvvm.ui.base.fragment.BaseFragmentBinding
import com.vn.basemvvm.ui.main.MainActivity

import javax.inject.Inject
import kotlin.random.Random

class BlankFragment : BaseFragmentBinding<BlankFragmentBinding, BlankViewModel>() {

    override val layoutId: Int
        get() = R.layout.blank_fragment

    @Inject
    lateinit var tempInject: TempInject
    @Inject lateinit var localStorage: LocalStorage

    val textView: TextView by bind(R.id.textFg)

    override fun init(view: View) {
        super.init(view)
        setShowLoading()
        localStorage.authorization = Random.nextInt(20000).toString()
        val color = Color.rgb(100 + Random.nextInt(155), 100 + Random.nextInt(155), 100 + Random.nextInt(155))
        view.setBackgroundColor(color)
        arguments?.let {
            textView.text = "Fragment " + it.getInt("1").toString()
        }
        tempInject.print()
        textView.setOnClickListener {
//            if (MainActivity.screen == 4) {
//                popToRoot()
//                MainActivity.screen = 0
//            } else {
                pushFragment(
                    BlankFragment::class,
                    bundleOf("1" to MainActivity.screen))
                MainActivity.screen += 1
//            }
        }

        viewModel.getData()

        viewModel.listData.observe(this, Observer {

        })
//        NetworkListener(context!!).observe(this, Observer { isConnected ->
//            Log.d("myInfo isConnected = ", "" + isConnected)
//        })
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
