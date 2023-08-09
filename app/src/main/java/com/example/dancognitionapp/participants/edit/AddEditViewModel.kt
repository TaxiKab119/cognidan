package com.example.dancognitionapp.participants.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantRepository

class AddEditViewModel(): ViewModel() {

    /**
     * This first block sets up the ui state to be mutable initializes participant list
     * */
    private val _uiState = mutableStateOf(
        AddEditUiState()
    )
    val uiState: State<AddEditUiState> = _uiState

    private val currentState: AddEditUiState
        get() = _uiState.value


//    fun selectParticipant(participant: Participant) {
//        _uiState.value = currentState.copy(
//            selectedParticipant = participant,
//            currentParticipantName = participant.name,
//            currentParticipantId = participant.id,
//            currentParticipantNotes = participant.notes,
//            isAddOrEdit = ParticipantScreenType.MODIFY
//        )
//        Timber.i("$_uiState")
//    }
//
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
        ParticipantRepository.participantList += newParticipant
        clearCurrentParticipantValues()
    }
//
//    fun editExistingParticipant() {
//        val editedParticipant: Participant = currentState.selectedParticipant!!.copy(
//            id = currentState.currentParticipantId,
//            name = currentState.currentParticipantName,
//            notes = currentState.currentParticipantNotes
//        )
//        currentState.participantList.replaceAll { participant ->
//            if (participant.internalId == currentState.selectedParticipant?.internalId) {
//                editedParticipant
//            } else {
//                participant
//            }
//        }
//        _uiState.value = currentState.copy(participantList = ParticipantRepository.participantList)
//        clearCurrentParticipantValues()
//    }
    fun clearCurrentParticipantValues() {
        _uiState.value = currentState.copy(
            currentParticipantName = "",
            currentParticipantId = "",
            currentParticipantNotes = "",
        )
    }
//
//    fun deleteParticipant() {
//        val unwantedParticipant = ParticipantRepository.participantList.find { participant ->
//            participant.internalId == currentState.selectedParticipant?.internalId
//        }
//        ParticipantRepository.participantList.remove(unwantedParticipant)
//        _uiState.value = currentState.copy(participantList = ParticipantRepository.participantList)
//        clearCurrentParticipantValues()
//    }
}