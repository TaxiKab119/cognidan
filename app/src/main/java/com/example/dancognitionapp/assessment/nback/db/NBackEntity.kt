package com.example.dancognitionapp.assessment.nback.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.nback.data.NBackClickCategorization

@Entity(tableName = "nback_trial_data")
data class NBackEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("trial_day") val trialDay: TrialDay,
    @ColumnInfo("trial_time") val trialTime: TrialTime,
    @ColumnInfo("participant_id") val participantId: Int
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
    SELECT * FROM nback_items 
    INNER JOIN nback_trial_data AS nBackEntity 
    WHERE nBackEntity.id = nback_entity_id
""")
data class NBackItemView(
    val position: Int,
    val blockNumber: Int,
    val nValue: Int,
    val reactionTime: Long?,
    val isTarget: Boolean,
    val categorization: NBackClickCategorization,
    val wasCorrectAction: Boolean,
    val nBackEntityId: Int
)