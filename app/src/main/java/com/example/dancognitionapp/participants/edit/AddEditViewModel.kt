package com.example.dancognitionapp.participants.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantRepository
import timber.log.Timber

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


    fun storeParticipantValues(participantInternalId: Int) {
        for (participant in ParticipantRepository.participantList) {
            Timber.i("Passed ID: $participantInternalId | List Id: ${participant.internalId} ")
            if (participant.internalId == participantInternalId) {
                _uiState.value = currentState.copy(
                    currentParticipantName = participant.name,
                    currentParticipantId = participant.id,
                    currentParticipantNotes = participant.notes,
                )
                break
            }
        }
    }
    fun populateParticipantFields(participantInternalId: Int) {
        val participant = ParticipantRepository.participantList.find { it.internalId == participantInternalId }
        _uiState.value = currentState.copy(
            currentParticipantName = participant?.name ?: "",
            currentParticipantId = participant?.id ?: "",
            currentParticipantNotes = participant?.notes ?: "",
        )
    }
    fun updateParticipantName(name: String) {
        _uiState.value = currentState.copy(
            currentParticipantName = name
        )
        Timber.i("${_uiState.value}")
    }

    fun updateParticipantId(id: String) {
        _uiState.value = currentState.copy(
            currentParticipantId = id
        )
    }

    fun updateParticipantNotes(notes: String) {
        _uiState.value = currentState.copy(
            currentParticipantNotes = notes
        )
    }

    fun appendNewParticipant() {
        val newParticipant = Participant(
            currentState.currentParticipantId,
            currentState.currentParticipantName,
            currentState.currentParticipantNotes
        )
        ParticipantRepository.participantList += newParticipant
    }

//    fun editExistingParticipant() {
//        val editedParticipant: Participant = currentState.copy(
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