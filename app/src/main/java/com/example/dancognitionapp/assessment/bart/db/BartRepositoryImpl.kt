package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow

class BartRepositoryImpl(private val bartDao: BartDao): BartRepository {
    override suspend fun insertBalloon(balloonEntity: BalloonEntity) {
        bartDao.insertBalloon(balloonEntity)
    }

    override suspend fun updateBalloonInflations(
        bartId: Int,
        listPosition: Int,
        newInflationCount: Int,
        didPop: Boolean
    ) {
        bartDao.updateBalloonByListPositionAndBartId(
            listPosition,
            bartId,
            newInflationCount,
            didPop
        )
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

    override suspend fun getBartTrialsByParticipantId(participantId: Int): Flow<List<BartEntity>> {
        return bartDao.getBartEntitiesForParticipant(participantId)
    }
}