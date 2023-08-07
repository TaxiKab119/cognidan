package com.example.dancognitionapp.participants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantUiState
import com.example.dancognitionapp.participants.data.participantList
import timber.log.Timber

class ParticipantsViewModel(): ViewModel() {
    /**
     * This first block sets up the ui state to be mutable initializes participant list
     * */
    private val _uiState = mutableStateOf(ParticipantUiState(participantList = participantList))
    val uiState: State<ParticipantUiState> = _uiState

    private val currentState: ParticipantUiState
        get() = _uiState.value

    fun selectParticipant(participant: Participant) {
        _uiState.value = currentState.copy(
            selectedParticipant = participant,
            currentParticipantName = participant.name,
            currentParticipantId = participant.id,
            currentParticipantNotes = participant.notes,
            isAddOrEdit = ParticipantScreenType.MODIFY
        )
        Timber.i("$_uiState")
    }

    fun addOrUpdateParticipantInfo(
        id: String? = null,
        name: String? = null,
        notes: String? = null
    ) {
        _uiState.value = currentState.copy(
            currentParticipantId = id ?: currentState.currentParticipantId,
            currentParticipantName = name ?: currentState.currentParticipantName,
            currentParticipantNotes = notes ?: currentState.currentParticipantNotes
        )
    }

    fun appendNewParticipant() {
        val newParticipant = Participant(
            currentState.currentParticipantId,
            currentState.currentParticipantName,
            currentState.currentParticipantNotes
        )
        participantList = currentState.participantList + newParticipant
        _uiState.value = currentState.copy(participantList = participantList)
        clearCurrentParticipantValues()
    }

    fun editExistingParticipant() {
        participantList = currentState.participantList.map { participant ->
            if (participant.internalId == currentState.selectedParticipant?.internalId) {
                participant.copy(
                    id = currentState.currentParticipantId,
                    name = currentState.currentParticipantName,
                    notes = currentState.currentParticipantNotes
                )
            } else {
                participant
            }
        }
        _uiState.value = currentState.copy(participantList = participantList)
    }
    fun clearCurrentParticipantValues() {
        _uiState.value = currentState.copy(
            currentParticipantName = "",
            currentParticipantId = "",
            currentParticipantNotes = "",
            selectedParticipant = null
        )
    }
    fun setAddScreen() {
        _uiState.value = currentState.copy(
            isAddOrEdit = ParticipantScreenType.ADD
        )
    }
}