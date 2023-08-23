package com.example.dancognitionapp.participants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.participants.data.ParticipantModel
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.data.ParticipantUiState
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ParticipantsViewModel(participantRepository: ParticipantRepository): ViewModel() {

    val uiState: StateFlow<ParticipantUiState> =
        participantRepository.getAllParticipantsStream().map { dbParticipantList ->
            val readableParticipantList = mutableListOf<ParticipantModel>()
            for (participant in dbParticipantList) {
                val participantModel = participant.toParticipantModel()
                readableParticipantList.add(participantModel)
            }
            ParticipantUiState(readableParticipantList)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ParticipantUiState()
            )
}

fun Participant.toParticipantModel(): ParticipantModel = ParticipantModel(
    id = userGivenId,
    name = name,
    notes = notes,
)