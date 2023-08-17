package com.example.dancognitionapp

import android.app.Application
import com.example.dancognitionapp.participants.db.ParticipantDatabaseBuilder
import timber.log.Timber

class DanCognitionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ParticipantDatabaseBuilder.createDb(applicationContext)
        Timber.plant(Timber.DebugTree())
    }
}