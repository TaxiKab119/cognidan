package com.example.dancognitionapp.assessment.selection

import android.os.Parcelable
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrialDetailsUiState(
    @Transient val participantList: List<Participant> = listOf(), // not needed to be sent via navArgs
    val selectedParticipant: Participant? = null,
    val selectedTrialTime: TrialTime? = null,
    val selectedTrialDay: TrialDay? = null
) : Parcelable