package com.example.dancognitionapp.assessment.nback.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization

@Entity(tableName = "nback_trials")
data class NBackEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("trial_day") val trialDay: TrialDay,
    @ColumnInfo("trial_time") val trialTime: TrialTime,
    @ColumnInfo("participant_id") val participantId: Int,
    @ColumnInfo("user_given_participant_id") val userGivenParticipantId: String,
)

@Entity(
    tableName = "nback_items",
    foreignKeys = [
        ForeignKey(
            entity = NBackEntity::class,
            parentColumns = ["id"],
            childColumns = ["nback_entity_id"]
        )
    ]

)
data class NBackItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("presentation_number") val position: Int,
    @ColumnInfo("block_number") val blockNumber: Int,
    @ColumnInfo("n_value") val nValue: Int,
    @ColumnInfo("reaction_time") val reactionTime: Long?,
    @ColumnInfo("is_target") val isTarget: Boolean,
    @ColumnInfo("categorization") val categorization: NBackClickCategorization,
    @ColumnInfo("was_correct_action") val wasCorrectAction: Boolean,
    @ColumnInfo("nback_entity_id") val nBackEntityId: Int
)

@DatabaseView("""
    SELECT user_given_participant_id AS dan_participant_id,
           trial_day,
           trial_time,
           block_number,
           n_value,
           presentation_number,
           reaction_time,
           is_target,
           categorization,
           was_correct_action,
           nback_entity_id AS trial_id
    FROM nback_items 
    INNER JOIN nback_trials
    ON nback_trials.id = nback_entity_id
""")
data class NBackTrialData(
    @ColumnInfo("dan_participant_id") val userGivenParticipantId: String,
    @ColumnInfo("trial_day") val trialDay: TrialDay,
    @ColumnInfo("trial_time") val trialTime: TrialTime,
    @ColumnInfo("block_number") val blockNumber: Int,
    @ColumnInfo("n_value") val nValue: Int,
    @ColumnInfo("presentation_number") val presentationNumber: Int,
    @ColumnInfo("reaction_time") val reactionTime: Long?,
    @ColumnInfo("is_target") val isTarget: Boolean,
    @ColumnInfo("categorization") val categorization: NBackClickCategorization,
    @ColumnInfo("was_correct_action") val wasCorrectAction: Boolean,
    @ColumnInfo("trial_id") val nBackEntityId: Int
)