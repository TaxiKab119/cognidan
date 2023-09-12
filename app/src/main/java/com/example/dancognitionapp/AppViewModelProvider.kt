package com.example.dancognitionapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dancognitionapp.assessment.bart.db.PracticeBartRepository
import com.example.dancognitionapp.assessment.bart.ui.BartViewModel
import com.example.dancognitionapp.assessment.nback.db.PracticeNBackRepository
import com.example.dancognitionapp.assessment.nback.ui.NBackViewModel
import com.example.dancognitionapp.assessment.selection.StartTrialViewModel
import com.example.dancognitionapp.assessment.selection.TrialDetailsViewModel
import com.example.dancognitionapp.participants.edit.AddEditViewModel
import com.example.dancognitionapp.participants.home.ParticipantsHomeViewModel
import com.example.dancognitionapp.participants.seetrialdata.ParticipantsTrialDataViewModel

object AppViewModelProvider {
    fun danAppViewModelFactory(isPractice: Boolean = false) = viewModelFactory {
        // Initializer for the ParticipantsViewModel
        initializer {
            ParticipantsHomeViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            AddEditViewModel(
                danCognitionApplication().container.participantRepository,
                bartRepository = danCognitionApplication().container.bartRepository,
                nBackRepository = danCognitionApplication().container.nBackRepository,
            )
        }
        initializer {
            ParticipantsTrialDataViewModel(
                bartRepository = danCognitionApplication().container.bartRepository,
                nBackRepository = danCognitionApplication().container.nBackRepository,
                participantRepository = danCognitionApplication().container.participantRepository,
            )
        }
        initializer {
            TrialDetailsViewModel(
                danCognitionApplication().container.participantRepository
            )
        }
        initializer {
            StartTrialViewModel(
                nBackRepository = danCognitionApplication().container.nBackRepository,
                bartRepository = danCognitionApplication().container.bartRepository
            )
        }
        initializer {
            BartViewModel(
                if (!isPractice) {
                    danCognitionApplication().container.bartRepository
                } else {
                    PracticeBartRepository()
                }
            )
        }
        initializer {
            NBackViewModel(
                if (!isPractice) {
                    danCognitionApplication().container.nBackRepository
                } else {
                    PracticeNBackRepository()
                }
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
