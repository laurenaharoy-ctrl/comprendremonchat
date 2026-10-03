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
        val nomChat = inputData.getString("nom_chat") ?: tr("votre chat", "your cat", "Ihre Katze")

        val channelId = "rappel_bilan_chat"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            tr("Rappels bien-être du chat", "Cat well-being reminders", "Erinnerungen zum Wohlbefinden der Katze"),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = tr("Rappels mensuels pour refaire le Bilan émotionnel", "Monthly reminders to redo the emotional report", "Monatliche Erinnerungen, die emotionale Einschätzung zu wiederholen")
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(tr("Il est temps de refaire le bilan !", "Time for a new report!", "Zeit für eine neue Einschätzung!"))
            .setContentText(tr("$nomChat a peut-être évolué ce dernier mois. Faites un nouveau Bilan émotionnel.", "$nomChat may have changed over the past month. Why not do a new emotional report?", "Bei $nomChat kann sich im letzten Monat einiges verändert haben. Machen Sie eine neue emotionale Einschätzung."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)

        return Result.success()
    }
}