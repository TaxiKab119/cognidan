package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime

class BartRepositoryImpl(private val bartDao: BartDao): BartRepository {
    override suspend fun insertBalloon(balloonEntity: BalloonEntity) {
        bartDao.insertBalloon(balloonEntity)
    }

    override suspend fun updateBalloon(balloonEntity: BalloonEntity) {
        bartDao.updateBalloon(balloonEntity)
    }

    override suspend fun insertBart(bartEntity: BartEntity?) {
        bartDao.insertBart(bartEntity)
    }

    override suspend fun getBartEntityByParticipantTrialData(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity? {
        return bartDao.getBartEntityForTrial(participantId, trialDay, trialTime)
    }
}