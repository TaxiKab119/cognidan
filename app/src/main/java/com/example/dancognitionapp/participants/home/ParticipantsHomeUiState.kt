package com.example.dancognitionapp.participants.home

import com.example.dancognitionapp.participants.db.Participant

data class ParticipantsHomeUiState(
    val participantList: List<Participant> = listOf()

)
