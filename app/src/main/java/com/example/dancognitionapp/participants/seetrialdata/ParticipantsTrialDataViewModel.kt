package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    private val fileBuilder = FileBuilder(bartRepository, nBackRepository)

    fun populateFields(participant: Participant, participantId: Int) {
        _uiState.value = currentState.copy(
            selectedParticipant = participant
        )
        constantlyUpdateTrials(participantId)
    }

    private fun constantlyUpdateTrials(participantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                bartRepository.getBartTrialsByParticipantId(participantId).collect {
                    _uiState.value = currentState.copy(
                        allBartTrials = it
                    )
                }
            }
            launch {
                nBackRepository.getNBackTrialsByParticipantId(participantId).collect {
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun shareSelectedOrAll(context: Context, key: String) {
        when(key) {
            "ALL" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    async {
                        _uiState.value = currentState.copy(
                            selectedBartTrialIds = currentState.allBartTrials.map { it.id },
                            selectedNBackTrialIds = currentState.allNBackTrials.map { it.id },
                        )
                    }.await()
                    exportFiles(context)
                }
            }
            "SELECTED" -> { exportFiles(context) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun exportFiles(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = fileBuilder.buildFiles(
                context,
                currentState.selectedParticipant.userGivenId,
                currentState.selectedBartTrialIds,
                currentState.selectedNBackTrialIds
            )
            files.forEach { file ->
                val intent = fileBuilder.goToFileIntent(context, file)
                context.startActivity(intent)
            }
        }
    }
}
