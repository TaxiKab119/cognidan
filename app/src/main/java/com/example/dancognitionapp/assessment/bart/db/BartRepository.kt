package com.example.dancognitionapp.assessment.bart.db

interface BartRepository {

    suspend fun insertBalloon(balloonEntity: BalloonEntity)

    suspend fun updateBalloon(balloonEntity: BalloonEntity)

    suspend fun insertBart(bartEntity: BartEntity?)

    suspend fun getBartEntityByParticipant(participantId: Int): BartEntity?
}