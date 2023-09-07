package com.example.dancognitionapp.assessment.nback.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization

class PracticeNBackRepository: NBackRepository {
    override suspend fun insertNBackItem(nBackItemEntity: NBackItemEntity) {
        /*DO NOTHING (no db actions for practice trials*/
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
        /*DO NOTHING (no db actions for practice trials*/
    }

    override suspend fun insertNBackTrial(nBackEntity: NBackEntity) {
        /*DO NOTHING (no db actions for practice trials*/
    }

    override suspend fun getNBackEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): NBackEntity? {
        /*DO NOTHING (no db actions for practice trials*/
        return null
    }
}