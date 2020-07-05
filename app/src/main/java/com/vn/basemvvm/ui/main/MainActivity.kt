package com.vn.basemvvm.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.BlockedNumberContract.BlockedNumbers
import android.provider.ContactsContract
import android.util.Log
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.vn.basemvvm.R
import com.vn.basemvvm.databinding.ActivityMainBinding
import com.vn.basemvvm.di.storage.frefs.LocalStorage
import com.vn.basemvvm.ui.base.activity.BaseActivityBinding
import com.vn.basemvvm.ui.blank.BlankFragment
import com.vn.basemvvm.utils.notification.NotificationCenter
import javax.inject.Inject


class MainActivity: BaseActivityBinding<ActivityMainBinding, MainViewModel>() {

    @Inject
    lateinit var storage: LocalStorage

    override val layoutId: Int = R.layout.activity_main
    companion object {
        var screen = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storage.authorization = null
//        viewModel = injectViewModel()
        dataBinding.viewModel = viewModel
//        val post = (0..3).map { Post(id = it, userId = 2, title = "danny", body = "hay hay")}
        storage.putData("new", 1)
//        dataBinding.button.setOnClickListener {
////            storage.setString(javaClass.simpleName, name.text.toString() + " - " + pass.text.toString())
////            show.text = storage.getString(javaClass.simpleName)
//            NotificationCenter.push(this, bundleOf( NotificationCenter.TITLE to "da co thong bao", NotificationCenter.MESSAGE to "chao cac ban"))
////            ConfirmDialog.show(supportFragmentManager, message = "", callback = {
////
////            })
////            startService(Intent(applicationContext, DownloadService::class.java))
////            ContextCompat.startForegroundService(applicationContext, Intent(applicationContext, DownloadService::class.java))
////            val p = storage.getData("new", Int::class.java)
////            print(p == null)
//            requestPermissions(100,  Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
//        }
//        dataBinding.next.setOnClickListener {
//            pushFragment(BlankFragment::class, bundleOf("1" to screen))
//            screen += 1
//        }
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.getData()
        viewModel.adapter.onClickedItem = {position, item ->
            Log.d("myInfo", "${position}")
            pushFragment(BlankFragment::class, bundleOf("1" to screen))
            screen += 1
        }
//        NetworkListener(applicationContext).observe(this, Observer { isConnected ->
//            Log.d("myInfo isConnected = ", "" + isConnected)
//        })
    }

    override fun initDelayed() {
        super.initDelayed()
        handleNotification(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleNotification(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun handleNotification(intent: Intent?) {
        val it = intent ?: return
        if (it.action == NotificationCenter.ACTION_NOTIFICATION) {
            val bundle = intent.getBundleExtra(NotificationCenter.EXTRA_TAG) ?: return
            Log.d("myInfo", "da co notification")
//            dataBinding.show.text = "Notification ID =" + bundle.getString("id")
        }
    }

    fun getList() {
        val c = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentResolver.query(
                BlockedNumbers.CONTENT_URI, arrayOf(
                    BlockedNumbers.COLUMN_ID, BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                    BlockedNumbers.COLUMN_E164_NUMBER
                ), null, null, null
            )
        } else {
//            contentResolver.query(
//                BlockedNumberContract.AUTHORITY_URI, arrayOf(
//                    BlockedNumbers.COLUMN_ID, BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
//                    BlockedNumbers.COLUMN_E164_NUMBER
//                ), null, null, null
//            )
        }
    }

    fun getContactName(phoneNumber: String, context: Context): String? {
        val uri: Uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )
        var contactName = ""
        val cursor: Cursor? = context.contentResolver.query(uri, arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME), null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0)
            }
            cursor.close()
        }
        return contactName
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

