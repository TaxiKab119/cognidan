package com.example.dancognitionapp

import android.content.Context
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.data.ParticipantRepositoryImpl
import com.example.dancognitionapp.participants.db.ParticipantDatabase

/**
 * App container for Dependency injection
 */
interface AppContainer {
    val participantRepository: ParticipantRepository
}

/**
 * [AppContainer] implementation that provides instance of [ParticipantRepositoryImpl]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ParticipantRepository]
     */
    override val participantRepository: ParticipantRepository by lazy {
        ParticipantRepositoryImpl(ParticipantDatabase.getDatabase(context).participantDao())
    }
}