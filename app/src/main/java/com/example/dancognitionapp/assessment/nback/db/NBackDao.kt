package com.example.dancognitionapp.assessment.nback.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartTrialData
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization
import kotlinx.coroutines.flow.Flow

@Dao
interface NBackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNBackItem(nBackItemEntity: NBackItemEntity)

    @Query("""
        DELETE FROM nback_items
        WHERE nback_entity_id = :nBackEntityId
    """)
    fun deleteNBackItemsByTrialId(nBackEntityId: Int)

    @Query(""" 
        DELETE FROM nback_items
        WHERE nback_entity_id IN (
            SELECT id FROM nback_trials
            WHERE participant_id = :participantId)
    """)
    fun deleteNBackItemsByParticipantId(participantId: Int)

    @Query("""
        UPDATE nback_items
        SET reaction_time = :reactionTime,
            categorization = :clickCategorization,
            was_correct_action = :wasCorrectAction
        WHERE nback_entity_id = :trialId
            AND presentation_number = :listPosition 
            AND block_number = :blockNumber 
            AND n_value = :nValue
    """)
    fun updateReactionTimeAndClickDetailsForItem(
        reactionTime: Long,
        trialId: Int,
        listPosition: Int,
        blockNumber: Int,
        nValue: Int,
        clickCategorization: NBackClickCategorization,
        wasCorrectAction: Boolean
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNBackTrial(nBackEntity: NBackEntity)

    @Query("""
        DELETE FROM nback_trials
        WHERE id = :nBackEntityId
    """)
    fun deleteNBackTrialEntitiesByTrialId(nBackEntityId: Int)

    @Query("""
        DELETE FROM nback_trials
        WHERE participant_id = :participantId
    """)
    fun deleteNBackTrialEntitiesByParticipantId(participantId: Int)

    @Query("""
        SELECT * FROM nback_trials
        WHERE participant_id = :participantId AND trial_day = :trialDay AND trial_time = :trialTime
    """)
    fun getNBackEntityForTrial(
        participantId: Int,
        trialDay: TrialDay,
        trialTime: TrialTime
    ): NBackEntity?
    @Query("""
        SELECT *
        FROM nback_trials
        WHERE participant_id = :participantId
        ORDER BY trial_day, 
                CASE trial_time
                    WHEN 'PRE_DIVE' THEN 1
                    WHEN 'DIVE' THEN 2
                    WHEN 'POST_DIVE' THEN 3
                    ELSE 9999
                END
    """)
    fun getNBackEntitiesForParticipant(participantId: Int): Flow<List<NBackEntity>>

    /**
     * Deletes all data related to NBack (in all tables in the database) for a given trialId: [Int]
     * ORDER MATTERS, must delete children before parents.
     * @param trialId is a unique identifier for every NBACK Trial
     */
    @Transaction
    fun deleteNBackTrialDataByTrialId(trialId: Int) {
        deleteNBackItemsByTrialId(trialId)
        deleteNBackTrialEntitiesByTrialId(trialId)
    }
    /**
     * Deletes all data related to NBack (in all tables in the database) for a given participantId: [Int]
     * ORDER MATTERS, must delete children before parents.
     * @param participantId is a unique internal identifier for every participant (separate from user_given_id)
     */
    @Transaction
    fun deleteNBackTrialDataByParticipantId(participantId: Int) {
        deleteNBackItemsByParticipantId(participantId)
        deleteNBackTrialEntitiesByParticipantId(participantId)
    }

    @Query("""
        SELECT * FROM NBackTrialData
        WHERE trial_id IN (:trialIds)
    """)
    fun getNBackTrialDataById(trialIds: List<Int>): List<NBackTrialData>

}