package com.example.dancognitionapp.participants.data

import com.example.dancognitionapp.participants.ParticipantScreenType

data class ParticipantUiState(
    val participantList: List<Participant>,
    val selectedParticipant: Participant? = null,
    val currentParticipantName: String = "",
    val currentParticipantId: String = "",
    val currentParticipantNotes: String = "",
    val isAddOrEdit: ParticipantScreenType = ParticipantScreenType.ADD
)
