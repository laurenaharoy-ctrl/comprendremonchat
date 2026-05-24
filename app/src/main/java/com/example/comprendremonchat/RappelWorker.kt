package com.laurena.comprendremonchat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class RappelWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val nomChat = inputData.getString("nom_chat") ?: "votre chat"

        val channelId = "rappel_bilan_chat"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Rappels bien-être du chat",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Rappels mensuels pour refaire le Bilan émotionnel"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Il est temps de refaire le bilan !")
            .setContentText("$nomChat a peut-être évolué ce dernier mois. Faites un nouveau Bilan émotionnel.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}