package com.example.dancognitionapp.participants.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.participants.data.ParticipantRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ParticipantsHomeViewModel(participantRepository: ParticipantRepository): ViewModel() {

    val uiState: StateFlow<ParticipantsHomeUiState> =
        participantRepository.getAllParticipantsStream().map { ParticipantsHomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ParticipantsHomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}