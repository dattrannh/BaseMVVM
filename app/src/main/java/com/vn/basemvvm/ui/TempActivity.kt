package com.vn.basemvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.vn.basemvvm.R
import com.vn.basemvvm.databinding.ActivityTempBinding
import com.vn.basemvvm.di.storage.database.AppDatabase
import com.vn.basemvvm.ui.base.activity.BaseActivityBiding
import com.vn.basemvvm.ui.base.adapter.BasePagerAdapter
import com.vn.basemvvm.ui.blank.BlankFragment
import com.vn.basemvvm.ui.main.MainActivity
import com.vn.basemvvm.utils.dpToPx
import javax.inject.Inject

class TempActivity : BaseActivityBiding<ActivityTempBinding, TempViewModel>() {



    override val layoutId: Int = R.layout.activity_temp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.button.setOnClickListener {
            viewModel.insert()
//            startActivity(Intent(this, MainActivity::class.java))
//            pushFragment(BlankFragment::class, bundleOf("1" to "1"))
        }
        dataBinding.viewPager.adapter = BasePagerAdapter()
        val indicatorView = dataBinding.indicator
        dataBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                indicatorView.update(10, position, positionOffset)
            }

            override fun onPageSelected(position: Int) {

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
