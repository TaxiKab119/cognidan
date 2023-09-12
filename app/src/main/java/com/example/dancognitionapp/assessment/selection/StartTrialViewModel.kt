package com.example.dancognitionapp.assessment.selection

import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository

class StartTrialViewModel(
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository
): ViewModel() {
    var doesTrialExist: Boolean = false
        private set

    suspend fun checkTrialExistence(trialData: TrialDetailsUiState) {
        val bartExists = bartRepository.getBartEntityByParticipantTrialData(
            trialData.selectedParticipant?.id ?: 0,
            trialData.selectedTrialDay ?: TrialDay.DAY_1,
            trialData.selectedTrialTime ?: TrialTime.PRE_DIVE
        ) != null

        val nBackExists = nBackRepository.getNBackEntityForTrial(
            trialData.selectedParticipant?.id ?: 0,
            trialData.selectedTrialDay ?: TrialDay.DAY_1,
            trialData.selectedTrialTime ?: TrialTime.PRE_DIVE
        ) != null

        doesTrialExist = bartExists || nBackExists
    }
}
