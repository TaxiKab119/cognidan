package com.example.dancognitionapp.assessment.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TrialDetailsViewModel(private val participantRepository: ParticipantRepository): ViewModel() {

    private val _uiState = MutableStateFlow(TrialDetailsUiState())
    val uiState: StateFlow<TrialDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            populateParticipants()
        }
    }

    private suspend fun populateParticipants() {
        val participantList = participantRepository.getAllParticipantsStream().first()
        _uiState.value = _uiState.value.copy(
            participantList = participantList
        )
    }

    fun selectParticipant(participant: Participant) {
        _uiState.value = _uiState.value.copy(
            selectedParticipant = participant
        )
    }
    fun selectTrialDay(trialDay: TrialDay) {
        _uiState.value = _uiState.value.copy(
            selectedTrialDay = trialDay
        )
    }
    fun selectTrialTime(trialTime: TrialTime) {
        _uiState.value = _uiState.value.copy(
            selectedTrialTime = trialTime
        )
    }
    fun areTrialDetailsFilled(): Boolean {
        return listOf(
            uiState.value.selectedTrialDay,
            uiState.value.selectedTrialTime,
            uiState.value.selectedParticipant
        ).all { it != null }
    }
}