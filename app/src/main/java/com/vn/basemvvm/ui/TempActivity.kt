package com.vn.basemvvm.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.vn.basemvvm.R
import com.vn.basemvvm.databinding.ActivityTempBinding
import com.vn.basemvvm.ui.base.activity.BaseActivityBiding
import com.vn.basemvvm.ui.blank.BlankFragment
import com.vn.basemvvm.ui.main.MainActivity

class TempActivity : BaseActivityBiding<ActivityTempBinding, TempViewModel>() {

    override val layoutId: Int = R.layout.activity_temp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
//            pushFragment(BlankFragment::class, bundleOf("1" to "1"))
        }
    }
}
