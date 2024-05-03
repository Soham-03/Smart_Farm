package com.example.smartfarm

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import kotlinx.coroutines.tasks.await

class FirestoreCheckWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("my-device")
            .document("week1")
            .collection("fields")
            .document("field1")// Set the correct path

        try {
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                val moistureValue = snapshot["moisture"].toString().toInt()
                if (moistureValue < 20) { // Check your threshold
                    NotificationHelper.createNotification(
                        applicationContext,
                        "Moisture Alert",
                        "Moisture level is low: $moistureValue%"
                    )
                }
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.retry()
        }
    }
}
