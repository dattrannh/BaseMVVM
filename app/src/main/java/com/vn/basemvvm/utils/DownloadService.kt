package com.vn.basemvvm.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.vn.basemvvm.R
import com.vn.basemvvm.utils.notification.NotificationCenter

class DownloadService : Service() {
    companion object {
        const val CHANNEL_ID = "BASEMVVM_DOWNLOAD"
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        downloadFile()
        return START_NOT_STICKY
    }

    private fun downloadFile() {
        val progressMax = 100
        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationCenter.createNotificationChannel(manager = notificationManager, channel = CHANNEL_ID)
        val id = System.currentTimeMillis().toInt()
        startForeground(id, notification.build())
        Thread(Runnable {
            SystemClock.sleep(2000)
            var progress = 0
            while (progress <= progressMax) {
                notification.setProgress(progressMax, progress, false);
                startForeground(id, notification.build());
                SystemClock.sleep(
                    1000
                )
                progress += 10
            }
            stopForeground(true)
            notification.setContentText("Download finished")
                .setProgress(0, 0, false)
                .setOngoing(false)
            notificationManager.notify(id, notification.build())
            // khi dismiss notification thì hệ thống tự thu hồi DownloadServices
            SystemClock.sleep(2000)
            stopSelf()
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}