package com.example.dancognitionapp.participants.data

data class ParticipantUiState(
    val participantList: List<Participant>,
    val selectedParticipant: Participant? = null,
    val currentParticipantName: String = "",
    val currentParticipantId: String = "",
    val currentParticipantNote: String = ""
)
