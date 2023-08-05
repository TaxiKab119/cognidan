package com.example.dancognitionapp.participants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantUiState
import com.example.dancognitionapp.participants.data.participants
import java.nio.file.Files.find

class ParticipantsViewModel(): ViewModel() {

    /**
     * This first block sets up the ui state to be mutable initializes participant list
     * */
    private val _uiState = mutableStateOf(ParticipantUiState(participantList = participants))
    val uiState: State<ParticipantUiState> = _uiState

    private val currentState: ParticipantUiState
        get() = _uiState.value


    fun selectParticipant(participant: Participant) {
        _uiState.value = currentState.copy(
            selectedParticipant = participant,
            currentParticipantName = participant.name,
            currentParticipantId = participant.id,
            currentParticipantNote = participant.notes
        )
    }

    fun addOrUpdateParticipantInfo(
        id: String? = null,
        name: String? = null,
        notes: String? = null
    ) {
        _uiState.value = currentState.copy(
            currentParticipantId = id ?: currentState.currentParticipantId,
            currentParticipantName = name ?: currentState.currentParticipantName,
            currentParticipantNote = notes ?: currentState.currentParticipantNote
        )
    }

    fun appendNewParticipant() {
        val newParticipant = Participant(
            currentState.currentParticipantId,
            currentState.currentParticipantName,
            currentState.currentParticipantNote
        )
        val updatedList = currentState.participantList + newParticipant
        _uiState.value = currentState.copy(participantList = updatedList)
        clearParticipantValues()
    }

    fun editExistingParticipant() {
        val updatedParticipants = currentState.participantList.map { participant ->
            if (participant.internalId == currentState.selectedParticipant?.internalId) {
                participant.copy(
                    id = currentState.currentParticipantId,
                    name = currentState.currentParticipantName,
                    notes = currentState.currentParticipantNote
                )
            } else {
                participant
            }
        }
        _uiState.value = currentState.copy(participantList = updatedParticipants)
    }
    fun clearParticipantValues() {
        _uiState.value = currentState.copy(
            currentParticipantName = "",
            currentParticipantId = "",
            currentParticipantNote = "",
            selectedParticipant = null
        )
    }
}