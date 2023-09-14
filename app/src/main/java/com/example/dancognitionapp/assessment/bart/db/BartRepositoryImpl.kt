package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

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

    override suspend fun deleteBartDataByParticipantId(participantId: Int) {
        bartDao.deleteBartTrialDataByParticipantId(participantId)
    }

    override suspend fun deleteBartDataByTrialId(trialId: Int) {
        bartDao.deleteBartTrialDataByTrialId(trialId)
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

    override suspend fun loadBartTrialData(
        participantId: String,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): Flow<List<BartTrialData>> {
        return flowOf(bartDao.loadBartTrialData(participantId, trialDay, trialTime))
    }
}