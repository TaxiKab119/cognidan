package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow

interface BartRepository {

    suspend fun insertBalloon(balloonEntity: BalloonEntity)

    suspend fun updateBalloonInflations(
        bartId: Int,
        listPosition: Int,
        newInflationCount: Int,
        didPop: Boolean
    )

    suspend fun insertBart(bartEntity: BartEntity?)

    suspend fun getBartEntityByParticipantTrialData(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity?

    suspend fun getBartTrialsByParticipantId(participantId: Int): Flow<List<BartEntity>>
}