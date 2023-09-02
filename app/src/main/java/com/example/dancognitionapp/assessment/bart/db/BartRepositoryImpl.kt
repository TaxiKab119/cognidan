package com.example.dancognitionapp.assessment.bart.db

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

    override suspend fun getBartEntityByParticipant(participantId: Int): BartEntity? {
        return bartDao.getBartEntityForParticipant(participantId)
    }
}