package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime

interface BartRepository {

    suspend fun insertBalloon(balloonEntity: BalloonEntity)

    suspend fun updateBalloon(balloonEntity: BalloonEntity)

    suspend fun insertBart(bartEntity: BartEntity?)

    suspend fun getBartEntityByParticipantTrialData(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity?
}