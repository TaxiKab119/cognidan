package com.example.dancognitionapp.assessment.bart.db

import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime

class PracticeBartRepository: BartRepository {
    override suspend fun insertBalloon(balloonEntity: BalloonEntity) {
        /*DO NOTHING (no db actions for practice trials*/
    }

    override suspend fun updateBalloonInflations(
        bartId: Int,
        listPosition: Int,
        newInflationCount: Int,
        didPop: Boolean
    ) {
        /*DO NOTHING (no db actions for practice trials*/
    }

    override suspend fun insertBart(bartEntity: BartEntity?) {
        /*DO NOTHING (no db actions for practice trials*/
    }

    override suspend fun getBartEntityByParticipantTrialData(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity? {
        /*DO NOTHING (no db actions for practice trials*/
        return null
    }
}