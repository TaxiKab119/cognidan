package com.example.dancognitionapp.participants.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.participants.data.ParticipantModel
import com.example.dancognitionapp.participants.data.ParticipantRepository
import timber.log.Timber

class AddEditViewModel(private val participantRepository: ParticipantRepository): ViewModel() {

    /**
     * This first block sets up the ui state to be mutable initializes participant list
     * */
    private val _uiState = mutableStateOf(
        AddEditUiState()
    )
    val uiState: State<AddEditUiState> = _uiState

    private val currentState: AddEditUiState
        get() = _uiState.value

//    fun populateParticipantFields(participantInternalId: Int) {
//        if (participantInternalId != 0) {
//            val participant = participantRepository.participantModelLists.find { it.internalId == participantInternalId }
//            _uiState.value = currentState.copy(
//                currentParticipantName = participant?.name ?: "",
//                currentParticipantId = participant?.id ?: "",
//                currentParticipantNotes = participant?.notes ?: "",
//            )
//        }
//    }
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

//    fun appendNewParticipant() {
//        val newParticipantModel = ParticipantModel(
//            currentState.currentParticipantId,
//            currentState.currentParticipantName,
//            currentState.currentParticipantNotes
//        )
//        ParticipantRepository.participantModelLists += newParticipantModel
//    }

    fun saveParticipant() {}
    fun deleteParticipant() {}

//    fun editExistingParticipant() {
//        val editedParticipant: ParticipantModel = currentState.copy(
//            id = currentState.currentParticipantId,
//            name = currentState.currentParticipantName,
//            notes = currentState.currentParticipantNotes
//        )
//        currentState.participantModelList.replaceAll { participant ->
//            if (participant.internalId == currentState.selectedParticipant?.internalId) {
//                editedParticipant
//            } else {
//                participant
//            }
//        }
//        _uiState.value = currentState.copy(participantModelList = ParticipantRepository.participantModelList)
//        clearCurrentParticipantValues()
//    }
//
//    fun deleteParticipant() {
//        val unwantedParticipant = ParticipantRepository.participantModelList.find { participant ->
//            participant.internalId == currentState.selectedParticipant?.internalId
//        }
//        ParticipantRepository.participantModelList.remove(unwantedParticipant)
//        _uiState.value = currentState.copy(participantModelList = ParticipantRepository.participantModelList)
//        clearCurrentParticipantValues()
//    }
}