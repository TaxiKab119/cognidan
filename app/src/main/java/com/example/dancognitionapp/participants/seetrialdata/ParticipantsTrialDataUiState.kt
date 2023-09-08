package com.example.dancognitionapp.participants.seetrialdata

import com.example.dancognitionapp.assessment.bart.db.BartEntity
import com.example.dancognitionapp.assessment.nback.db.NBackEntity

data class ParticipantsTrialDataUiState(
    val selectedTrialIds: List<Int> = listOf(),
    val allBartTrials: List<BartEntity> = listOf(),
    val allNBackTrials: List<NBackEntity> = listOf()
)
