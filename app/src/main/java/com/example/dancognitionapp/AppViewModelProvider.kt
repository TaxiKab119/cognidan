package com.example.dancognitionapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dancognitionapp.participants.ParticipantsViewModel
import com.example.dancognitionapp.participants.edit.AddEditViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for the ParticipantsViewModel
        initializer {
            ParticipantsViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            AddEditViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [DanCognitionApplication].
 */
fun CreationExtras.danCognitionApplication(): DanCognitionApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DanCognitionApplication)
