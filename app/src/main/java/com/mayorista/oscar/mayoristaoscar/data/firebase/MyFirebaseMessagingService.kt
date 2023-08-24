package com.mayorista.oscar.mayoristaoscar.data.firebase

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mayorista.oscar.mayoristaoscar.R



class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("bruno", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val iconBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_mayooscar_round)

        val channelId = CHANNEL_ID
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setLargeIcon(iconBitmap)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    companion object {
        private const val CHANNEL_ID = "my_channel_id"
        private const val NOTIFICATION_ID = 1
    }
}