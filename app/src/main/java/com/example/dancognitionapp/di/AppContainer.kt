package com.example.dancognitionapp.di

import android.content.Context
import com.example.dancognitionapp.assessment.bart.db.BartDatabase
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.bart.db.BartRepositoryImpl
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.data.ParticipantRepositoryImpl
import com.example.dancognitionapp.participants.db.ParticipantDatabase

/**
 * App container for Dependency injection
 */
interface AppContainer {
    val participantRepository: ParticipantRepository
    val bartRepository: BartRepository
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

    /**
     * Implementation for [BartRepository]
     */
    override val bartRepository: BartRepository by lazy {
        BartRepositoryImpl(BartDatabase.getDatabase(context).bartDao())
    }
}