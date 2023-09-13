package com.example.dancognitionapp.assessment.bart.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import kotlinx.coroutines.flow.Flow

@Dao
interface BartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalloon(balloonEntity: BalloonEntity)

    @Query("""
        DELETE FROM balloon_table
        WHERE bart_entity_id = :bartEntityId
    """)
    fun deleteBalloonsByTrialId(bartEntityId: Int)

    @Query(""" 
        DELETE FROM balloon_table
        WHERE bart_entity_id IN (
            SELECT id FROM bart_trials
            WHERE participant_id = :participantId)
    """)
    fun deleteBalloonsByParticipantId(participantId: Int)

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
        DELETE FROM bart_trials
        WHERE id = :bartEntityId
    """)
    fun deleteBartTrialEntitiesByTrialId(bartEntityId: Int)

    @Query("""
        DELETE FROM bart_trials
        WHERE participant_id = :participantId
    """)
    fun deleteBartTrialEntitiesByParticipantId(participantId: Int)

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
        ORDER BY trial_day, 
                CASE trial_time
                    WHEN 'PRE_DIVE' THEN 1
                    WHEN 'DIVE' THEN 2
                    WHEN 'POST_DIVE' THEN 3
                    ELSE 9999
                END
    """)
    fun getBartEntitiesForParticipant(participantId: Int): Flow<List<BartEntity>>

    /**
     * Deletes all data related to Bart (in all tables in the database) for a given trialId: [Int]
     * ORDER MATTERS, must delete children before parents.
     * @param trialId is a unique identifier for every BART Trial
     */
    @Transaction
    fun deleteBartTrialDataByTrialId(trialId: Int) {
        deleteBalloonsByTrialId(trialId)
        deleteBartTrialEntitiesByTrialId(trialId)
    }

    /**
     * Deletes all data related to Bart (in all tables in the database) for a given participantId: [Int]
     * ORDER MATTERS, must delete children before parents.
     * @param participantId is a unique internal identifier for every participant (separate from user_given_id)
     */
    @Transaction
    fun deleteBartTrialDataByParticipantId(participantId: Int) {
        deleteBalloonsByParticipantId(participantId)
        deleteBartTrialEntitiesByParticipantId(participantId)
    }
}