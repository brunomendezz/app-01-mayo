package com.mayorista.oscar.mayoristaoscar.data.firebase

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mayorista.oscar.mayoristaoscar.R

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d("bruno", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.

    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // ...
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
           // .setSmallIcon(R.mipmap.ic_mayooscar1) // Establecer el ícono de la notificación
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)

        // Configurar otras propiedades de la notificación

        // Mostrar la notificación
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
    companion object {
        private const val CHANNEL_ID = "my_channel_id"
        private const val NOTIFICATION_ID = 1
    }
}