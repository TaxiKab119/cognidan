package com.example.dancognitionapp.assessment.bart.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalloon(balloonEntity: BalloonEntity)

    @Update
    fun updateBalloon(balloonEntity: BalloonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBart(bartEntity: BartEntity?)

    @Query("SELECT * FROM bart_trial_data WHERE participant_id = :participantId")
    fun getBartEntityForParticipant(participantId: Int): BartEntity?
}