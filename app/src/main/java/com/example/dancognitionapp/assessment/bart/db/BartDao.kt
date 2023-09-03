package com.example.dancognitionapp.assessment.bart.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime

@Dao
interface BartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalloon(balloonEntity: BalloonEntity)

    @Update
    fun updateBalloon(balloonEntity: BalloonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBart(bartEntity: BartEntity?)

    // LIMIT 1 so that if there were was already an existing data it would just be overwritten
    @Query("SELECT * FROM bart_trial_data WHERE participant_id = :participantId AND trial_day = :trialDay AND trial_time = :trialTime LIMIT 1")
    fun getBartEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity?
}