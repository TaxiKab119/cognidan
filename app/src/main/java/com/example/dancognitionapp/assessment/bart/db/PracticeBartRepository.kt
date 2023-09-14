package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PracticeBartRepository: BartRepository {
    override suspend fun insertBalloon(balloonEntity: BalloonEntity) {
        /* DO NOTHING (no db actions for practice trials) */
    }

    override suspend fun updateBalloonInflations(
        bartId: Int,
        listPosition: Int,
        newInflationCount: Int,
        didPop: Boolean
    ) {
        /* DO NOTHING (no db actions for practice trials) */
    }

    override suspend fun insertBart(bartEntity: BartEntity?) {
        /* DO NOTHING (no db actions for practice trials) */
    }

    override suspend fun deleteBartDataByParticipantId(participantId: Int) {
        /* DO NOTHING (no db actions for practice trials) */
    }

    override suspend fun deleteBartDataByTrialId(trialId: Int) {
        /* DO NOTHING (no db actions for practice trials) */
    }

    override suspend fun getBartEntityByParticipantTrialData(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity? {
        /* DO NOTHING (no db actions for practice trials) */
        return null
    }

    override suspend fun getBartTrialsByParticipantId(participantId: Int): Flow<List<BartEntity>> {
        /* DO NOTHING (no db actions for practice trials) */
        return emptyFlow()
    }

    override suspend fun loadBartTrialData(
        participantId: String,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): Flow<List<BartTrialData>> {
        return emptyFlow()
    }

    override suspend fun getBartTrialDataByTrialIds(trialIds: List<Int>): Flow<List<BartTrialData>> {
        /* DO NOTHING (no db actions for practice trials) */
        return emptyFlow()
    }
}