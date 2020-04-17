/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vn.basemvvm.utils.notification

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateUtils
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.vn.basemvvm.BuildConfig
import com.vn.basemvvm.R
import com.vn.basemvvm.ui.main.MainActivity
import com.vn.basemvvm.utils.NotificationIntentService
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


object NotificationCenter {
    
    const val ACTION_NOTIFICATION = BuildConfig.APPLICATION_ID + ".notification"
    const val CHANNEL_ID = BuildConfig.APPLICATION_ID
    const val EXTRA_TAG = "notification"
    const val MESSAGE = "message"
    const val TITLE = "title"

    @JvmStatic
    fun push(context: Context, bundle: Bundle) {
        val con = context.applicationContext ?: return
        val notificationId = System.currentTimeMillis().toInt()
        val isForeground =  isApplicationInForeground(con)

        bundle.putBoolean("foreground", isForeground)
        bundle.putString("id", notificationId.toString() + "")

        val title = bundle.getString(TITLE)
        val message = bundle.getString(MESSAGE)
        val intent = Intent()
        intent.action = ACTION_NOTIFICATION
        intent.setClass(con, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(EXTRA_TAG, bundle)
        val pendingIntent = PendingIntent.getActivity(con, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(con, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(title)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(sound)
            .setLargeIcon(getBitmap(null))
            .setContentIntent(pendingIntent)
            .setCustomContentView(setupCollapsedView(context))
//            .setCustomBigContentView(setupExpandedView(context))
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        val manager = con.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(manager, CHANNEL_ID)
        manager.notify(notificationId, builder.build())
    }



    open fun createNotificationChannel(manager: NotificationManager, channel: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "BaseMVVM"
            val description = "Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channel, name, importance)
            notificationChannel.description = description
            manager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getBitmap(url: String?): Bitmap? {
        val link = url ?: return null
        var inputStream: InputStream? = null
        try {
            val mURL = URL(link)
            inputStream = mURL.openStream()
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    @JvmStatic
    fun isApplicationInForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val processes = activityManager.runningAppProcesses ?: return false
        for (processInfo in processes) {
            if (processInfo.processName == context.packageName && processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }

    fun setupExpandedView(context: Context) : RemoteViews {
        val expandedView = RemoteViews(context.packageName, R.layout.notification_expanded)
        expandedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
        expandedView.setTextViewText(R.id.notification_message, "chao cac ban")
        // adding action to left button
        val leftIntent = Intent(context, NotificationIntentService::class.java)
        leftIntent.action = "left"
        expandedView.setOnClickPendingIntent(R.id.left_button, PendingIntent.getService(context, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT))
        // adding action to right button
        val rightIntent = Intent(context, NotificationIntentService::class.java)
        rightIntent.action = "right"
        expandedView.setOnClickPendingIntent(R.id.right_button, PendingIntent.getService(context, 1, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT))
        return expandedView
    }

    fun setupCollapsedView(context: Context) : RemoteViews {
        val collapsedView = RemoteViews(context.packageName, R.layout.notification_collapsed)
        collapsedView.setTextViewText(R.id.timestamp, DateUtils.formatDateTime( context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME))
        return collapsedView
    }


}