package com.example.dancognitionapp.participants.seetrialdata

import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first

class ParticipantsTrialDataViewModel(
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository,
    private val participantRepository: ParticipantRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(
        ParticipantsTrialDataUiState()
    )
    val uiState: StateFlow<ParticipantsTrialDataUiState> = _uiState

    private val currentState: ParticipantsTrialDataUiState
        get() = _uiState.value

    suspend fun populateFields(participant: Participant) {
        _uiState.value = currentState.copy(
            selectedParticipant = participant,
            allBartTrials = bartRepository.getBartTrialsByParticipantId(participant.id).first(),
            allNBackTrials = nBackRepository.getNBackTrialsByParticipantId(participant.id).first()
        )
    }

    /**
     * Toggles the trial being added to its respective BART or NBACK list when
     * a user checks the checkbox.
     * @param trialIdentifier Each checkbox has a corresponding [TrialIdentifier]
     */
    fun toggleToSelectedTrialsList(trialIdentifier: TrialIdentifier) {
        when(trialIdentifier.testType) {
            TestType.BART -> {
                if (trialIdentifier.trialId in currentState.selectedBartTrialIds) {
                    _uiState.value = currentState.copy(
                        selectedBartTrialIds = currentState.selectedBartTrialIds - trialIdentifier.trialId
                    )
                } else {
                    _uiState.value = currentState.copy(
                        selectedBartTrialIds = currentState.selectedBartTrialIds + trialIdentifier.trialId
                    )
                }
            }
            TestType.NBACK -> {
                if (trialIdentifier.trialId in currentState.selectedNBackTrialIds) {
                    _uiState.value = currentState.copy(
                        selectedNBackTrialIds = currentState.selectedNBackTrialIds - trialIdentifier.trialId
                    )
                } else {
                    _uiState.value = currentState.copy(
                        selectedNBackTrialIds = currentState.selectedNBackTrialIds + trialIdentifier.trialId
                    )
                }
            }
        }
    }

    fun deleteTrial(trialIdentifier: TrialIdentifier) {
        when(trialIdentifier.testType){
            TestType.BART -> {}
            TestType.NBACK -> {}
        }
    }
}