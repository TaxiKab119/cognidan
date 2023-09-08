package com.example.dancognitionapp.assessment.bart.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow

@Dao
interface BartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalloon(balloonEntity: BalloonEntity)

    @Query("""
        UPDATE balloon_table
        SET number_of_inflations = :newInflationCount,
            did_pop = CASE WHEN :didPop = 0 THEN did_pop ELSE :didPop END
        WHERE balloon_number = :listPosition AND bart_entity_id = :bartId
    """)
    fun updateBalloonByListPositionAndBartId(
        listPosition: Int,
        bartId: Int,
        newInflationCount: Int,
        didPop: Boolean
    )


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBart(bartEntity: BartEntity?)

    @Query("""
        SELECT * FROM bart_trials
        WHERE participant_id = :participantId AND trial_day = :trialDay AND trial_time = :trialTime
    """)
    fun getBartEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): BartEntity?

    @Query("""
        SELECT *
        FROM bart_trials
        WHERE participant_id = :participantId
        ORDER BY trial_day AND trial_time
    """)
    fun getBartEntitiesForParticipant(participantId: Int): Flow<List<BartEntity>>
}