package com.example.dancognitionapp.assessment.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.properties.Delegates

class StartTrialViewModel(
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository
): ViewModel() {

    private var _startTrialScreenState: MutableStateFlow<StartTrialScreenState> = MutableStateFlow(StartTrialScreenState.LOADING)
    val startTrialScreenState: StateFlow<StartTrialScreenState> = _startTrialScreenState.asStateFlow()
    suspend fun checkTrialExistence(trialData: TrialDetailsUiState) {
        Timber.i("Initial ScreenState: ${startTrialScreenState.value}")
        val trialParticipant = trialData.selectedParticipant?.id
        val trialDay = trialData.selectedTrialDay
        val trialTime = trialData.selectedTrialTime
        _startTrialScreenState.value = withContext(Dispatchers.IO) {
            if (bartRepository.getBartEntityByParticipantTrialData(
                trialParticipant ?: 0,
                trialDay ?: TrialDay.DAY_1,
                trialTime ?: TrialTime.PRE_DIVE) != null ||
                    nBackRepository.getNBackEntityForTrial(
                        trialParticipant ?: 0,
                        trialDay ?: TrialDay.DAY_1,
                        trialTime ?: TrialTime.PRE_DIVE
                    ) != null) {
                StartTrialScreenState.EXISTS
            } else {
                StartTrialScreenState.NOT_EXIST
            }
        }
        Timber.i("Final ScreenState: ${startTrialScreenState.value}")
    }
}
