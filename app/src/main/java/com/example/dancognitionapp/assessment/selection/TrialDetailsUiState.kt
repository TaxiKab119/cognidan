package com.example.dancognitionapp.assessment.selection

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.participants.db.Participant

data class TrialDetailsUiState(
    val participantList: List<Participant> = listOf(),
    val selectedParticipant: Participant? = null,
    val selectedTrialTime: TrialTime? = null,
    val selectedTrialDay: TrialDay? = null
)
