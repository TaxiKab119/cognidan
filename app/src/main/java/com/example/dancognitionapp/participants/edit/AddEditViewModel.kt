package com.example.dancognitionapp.participants.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.participants.data.ParticipantDetails
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

class AddEditViewModel(
    private val participantRepository: ParticipantRepository
): ViewModel() {
    /**
     * This first block sets up the ui state to be mutable initializes participant list
     * */
    private val _uiState = MutableStateFlow(
        AddEditUiState()
    )
    val uiState: StateFlow<AddEditUiState> = _uiState

    private val currentState: AddEditUiState
        get() = _uiState.value

    fun populateParticipantFields(selectedParticipant: Participant?) {
        val populatedState = selectedParticipant?.toAddEditUiState(selectedParticipant.id) ?: AddEditUiState()
        _uiState.value = populatedState
        Timber.i("Initial Ui State: ${uiState.value}")
        _uiState.value = currentState.copy(haveFieldsBeenPopulated = true)
    }
    fun updateUiState(participantDetails: ParticipantDetails) {
        _uiState.value = currentState.copy(
            participantDetails = participantDetails
        )
    }
    suspend fun updateParticipant() {
        if (validateInput()) {
            participantRepository.updateParticipant(currentState.participantDetails.toParticipantEntity())
        }
    }

    suspend fun saveNewParticipant() {
        if (validateInput()) {
            participantRepository.insertParticipant(
                Participant(
                    userGivenId = currentState.participantDetails.userGivenId,
                    name = currentState.participantDetails.name,
                    notes = currentState.participantDetails.notes,
                )
            )
        }
    }
    suspend fun deleteParticipant() {
        participantRepository.deleteParticipant(currentState.participantDetails.toParticipantEntity())
    }

    /**
     * Checks to see if name and id are empty (required for Database).
     * Used to verify user input before adding or updating the entity in the db
     * */
    private fun validateInput(uiState: ParticipantDetails = currentState.participantDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && userGivenId.isNotBlank()
        }
    }
    private fun Participant.toParticipantDetails(): ParticipantDetails = ParticipantDetails(
        userGivenId = userGivenId,
        name = name,
        notes = notes
    )

    private fun Participant.toAddEditUiState(participantInternalId: Int): AddEditUiState = AddEditUiState(
        participantDetails = this.toParticipantDetails(),
        participantInternalId = participantInternalId,
    )

    private fun ParticipantDetails.toParticipantEntity(): Participant = Participant(
        id = currentState.participantInternalId,
        userGivenId = userGivenId,
        name = name,
        notes = notes
    )
}