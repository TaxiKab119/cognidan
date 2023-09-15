package com.example.dancognitionapp.assessment.nback.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NBackRepositoryImpl(private val nBackDao: NBackDao): NBackRepository {
    override suspend fun insertNBackItem(nBackItemEntity: NBackItemEntity) {
        nBackDao.insertNBackItem(nBackItemEntity)
    }

    override suspend fun updateReactionTimeAndClickDetailsForItem(
        reactionTime: Long,
        trialId: Int,
        listPosition: Int,
        blockNumber: Int,
        nValue: Int,
        clickCategorization: NBackClickCategorization,
        wasCorrectAction: Boolean
    ) {
        nBackDao.updateReactionTimeAndClickDetailsForItem(
            reactionTime,
            trialId,
            listPosition,
            blockNumber,
            nValue,
            clickCategorization,
            wasCorrectAction
        )
    }

    override suspend fun insertNBackTrial(nBackEntity: NBackEntity) {
        nBackDao.insertNBackTrial(nBackEntity)
    }

    override suspend fun deleteNBackDataByParticipantId(participantId: Int) {
        nBackDao.deleteNBackTrialDataByParticipantId(participantId)
    }

    override suspend fun deleteNBackDataByTrialId(trialId: Int) {
        nBackDao.deleteNBackTrialDataByTrialId(trialId)
    }

    override suspend fun getNBackEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): NBackEntity? {
        return nBackDao.getNBackEntityForTrial(participantId, trialDay, trialTime)
    }

    override suspend fun getNBackTrialsByParticipantId(participantId: Int): Flow<List<NBackEntity>> {
        return nBackDao.getNBackEntitiesForParticipant(participantId)
    }

    override suspend fun getNBackTrialsByTrialIds(trialIds: List<Int>): Flow<List<NBackTrialData>> {
        return flowOf(nBackDao.getNBackTrialDataById(trialIds))
    }

}