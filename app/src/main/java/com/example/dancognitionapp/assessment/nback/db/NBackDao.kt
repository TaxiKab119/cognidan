package com.example.dancognitionapp.assessment.nback.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization
import kotlinx.coroutines.flow.Flow

@Dao
interface NBackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNBackItem(nBackItemEntity: NBackItemEntity)

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

}