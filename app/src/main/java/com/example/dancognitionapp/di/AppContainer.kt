package com.example.dancognitionapp.di

import android.content.Context
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.bart.db.BartRepositoryImpl
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepositoryImpl
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.data.ParticipantRepositoryImpl
import com.example.dancognitionapp.participants.db.CogniDanDatabase

/**
 * App container for Dependency injection
 */
interface AppContainer {
    val participantRepository: ParticipantRepository
    val bartRepository: BartRepository
    val nBackRepository: NBackRepository
}

/**
 * [AppContainer] implementation that provides instance of [ParticipantRepositoryImpl]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ParticipantRepository]
     */
    override val participantRepository: ParticipantRepository by lazy {
        ParticipantRepositoryImpl(CogniDanDatabase.getDatabase(context).participantDao())
    }

    /**
     * Implementation for [BartRepository]
     */
    override val bartRepository: BartRepository by lazy {
        BartRepositoryImpl(CogniDanDatabase.getDatabase(context).bartDao())
    }

    /**
     * Implementation for [NBackRepository]
     */
    override val nBackRepository: NBackRepository by lazy {
        NBackRepositoryImpl(CogniDanDatabase.getDatabase(context).nBackDao())
    }
}