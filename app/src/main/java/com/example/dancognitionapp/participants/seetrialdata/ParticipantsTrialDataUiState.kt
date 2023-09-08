package com.example.dancognitionapp.participants.seetrialdata

import com.example.dancognitionapp.assessment.bart.db.BartEntity
import com.example.dancognitionapp.assessment.nback.db.NBackEntity
import com.example.dancognitionapp.participants.db.Participant

data class ParticipantsTrialDataUiState(
    val selectedParticipant: Participant = Participant.emptyParticipant,
    val selectedBartTrialIds: List<Int> = listOf(),
    val selectedNBackTrialIds: List<Int> = listOf(),
    val allBartTrials: List<BartEntity> = listOf(),
    val allNBackTrials: List<NBackEntity> = listOf()
)
