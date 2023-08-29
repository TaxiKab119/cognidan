package com.example.dancognitionapp.participants.edit

import com.example.dancognitionapp.participants.data.ParticipantDetails

data class AddEditUiState(
    val participantDetails: ParticipantDetails = ParticipantDetails(),
    val haveFieldsBeenPopulated: Boolean = false,
    val participantInternalId: Int = 0
)
