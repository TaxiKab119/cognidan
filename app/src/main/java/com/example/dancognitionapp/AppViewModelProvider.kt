package com.example.dancognitionapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dancognitionapp.assessment.bart.ui.BartViewModel
import com.example.dancognitionapp.assessment.nback.ui.NBackViewModel
import com.example.dancognitionapp.assessment.selection.TrialDetailsViewModel
import com.example.dancognitionapp.participants.edit.AddEditViewModel
import com.example.dancognitionapp.participants.home.ParticipantsHomeViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        // Initializer for the ParticipantsViewModel
        initializer {
            ParticipantsHomeViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            AddEditViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            TrialDetailsViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            BartViewModel(
                danCognitionApplication().container.bartRepository
            )
        }
        initializer {
            NBackViewModel(
                danCognitionApplication().container.nBackRepository
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
