package com.vn.basemvvm.ui.temp

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.vn.basemvvm.R
import com.vn.basemvvm.databinding.ActivityTempBinding
import com.vn.basemvvm.ui.base.activity.BaseActivityBinding
import com.vn.basemvvm.ui.main.MainActivity

class TempActivity : BaseActivityBinding<ActivityTempBinding, TempViewModel>() {



    override val layoutId: Int = R.layout.activity_temp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.viewModel = viewModel
        dataBinding.button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        dataBinding.viewPager.adapter = TempPagerAdapter()
        dataBinding.viewPager.addOnPageChangeListener(object :
            ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                dataBinding.indicator.update(10, position, positionOffset)
            }
        })

    }



    override fun viewDidLoad() {
//        mHandler.postDelayed({
//            dataBinding.imageView.cornerRadius = dpToPx(10f)
//        } , 1000)

    }
    override fun onResume() {
        super.onResume()
    }
}
