package com.example.dancognitionapp.assessment.nback.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization
import kotlinx.coroutines.flow.Flow

interface NBackRepository {

    suspend fun insertNBackItem(nBackItemEntity: NBackItemEntity)

    suspend fun updateReactionTimeAndClickDetailsForItem(
        reactionTime: Long,
        trialId: Int,
        listPosition: Int,
        blockNumber: Int,
        nValue: Int,
        clickCategorization: NBackClickCategorization,
        wasCorrectAction: Boolean
    )

    suspend fun insertNBackTrial(nBackEntity: NBackEntity)

    suspend fun getNBackEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): NBackEntity?

    suspend fun getNBackTrialsByParticipantId(participantId: Int): Flow<List<NBackEntity>>
}