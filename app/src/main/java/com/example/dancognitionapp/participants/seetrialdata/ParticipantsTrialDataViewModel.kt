package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ParticipantsTrialDataViewModel(
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(
        ParticipantsTrialDataUiState()
    )
    val uiState: StateFlow<ParticipantsTrialDataUiState> = _uiState

    private val currentState: ParticipantsTrialDataUiState
        get() = _uiState.value

    private val fileBuilder = FileBuilder(viewModelScope, bartRepository, nBackRepository)

    fun populateFields(participant: Participant) {
        _uiState.value = currentState.copy(
            selectedParticipant = participant
        )
        constantlyUpdateTrials(participant)
    }

    private fun constantlyUpdateTrials(participant: Participant) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                bartRepository.getBartTrialsByParticipantId(participant.id).collect {
                    _uiState.value = currentState.copy(
                        allBartTrials = it
                    )
                }
            }
            launch {
                nBackRepository.getNBackTrialsByParticipantId(participant.id).collect {
                    _uiState.value = currentState.copy(
                        allNBackTrials = it
                    )
                }
            }
        }
    }

    /**
     * Toggles the trial being added to its respective BART or NBACK list when
     * a user checks the checkbox.
     * @param trialIdentifier Each checkbox has a corresponding [TrialIdentifier]
     */
    fun toggleToSelectedTrialsList(trialIdentifier: TrialIdentifier, trialDataFields: TrialDataFields) {
        val csvFileParams = CSVFileParams(
            participantName = trialDataFields.participantName,
            participantId = trialDataFields.danParticipantId,
            testType = trialDataFields.testType,
            trialTime = trialDataFields.trialTime,
            trialDay = trialDataFields.trialDay
        )
        when(trialIdentifier.testType) {
            TestType.BART -> {
                if (trialIdentifier.trialId in currentState.selectedBartTrialIds) {
                    _uiState.value = currentState.copy(
                        selectedBartTrialIds = currentState.selectedBartTrialIds - trialIdentifier.trialId
                    )
                    fileBuilder.removeFile(csvFileParams)
                } else {
                    _uiState.value = currentState.copy(
                        selectedBartTrialIds = currentState.selectedBartTrialIds + trialIdentifier.trialId
                    )
                    fileBuilder.addFile(csvFileParams)
                }
            }
            TestType.NBACK -> {
                if (trialIdentifier.trialId in currentState.selectedNBackTrialIds) {
                    _uiState.value = currentState.copy(
                        selectedNBackTrialIds = currentState.selectedNBackTrialIds - trialIdentifier.trialId
                    )
                    fileBuilder.removeFile(csvFileParams)
                } else {
                    _uiState.value = currentState.copy(
                        selectedNBackTrialIds = currentState.selectedNBackTrialIds + trialIdentifier.trialId
                    )
                    fileBuilder.addFile(csvFileParams)
                }
            }
        }
    }

    fun deleteTrial(trialIdentifier: TrialIdentifier) {
        when(trialIdentifier.testType){
            TestType.BART -> {
                viewModelScope.launch(Dispatchers.IO) {
                    bartRepository.deleteBartDataByTrialId(trialIdentifier.trialId)
                }
            }
            TestType.NBACK -> {
                viewModelScope.launch(Dispatchers.IO) {
                    nBackRepository.deleteNBackDataByTrialId(trialIdentifier.trialId)
                }
            }
        }
    }

    fun downloadFiles(context: Context) {
        val files = fileBuilder.buildFiles(context)
        val intent = fileBuilder.goToFileIntent(context, files.first())
        context.startActivity(intent)
    }
}