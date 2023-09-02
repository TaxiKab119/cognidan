package com.example.dancognitionapp.assessment.bart.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import java.util.UUID

@Entity(tableName = "bart_trial_data")
data class BartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("trial_day") val trialDay: TrialDay,
    @ColumnInfo("trial_time") val trialTime: TrialTime,
    @ColumnInfo("participant_id") val participantId: Int
)

@Entity(tableName = "balloon_table",
    foreignKeys = [
        ForeignKey(
            entity = BartEntity::class,
            parentColumns = ["participant_id"],
            childColumns = ["bart_participant_id"]
        )
    ]
)
data class BalloonEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo("balloon_number") val balloonNumber: Int,
    @ColumnInfo("max_inflations") val maxInflations: Int,
    @ColumnInfo("number_of_inflations") val numberOfInflations: Int,
    @ColumnInfo("did_pop") val didPop: Boolean,
    @ColumnInfo("bart_participant_id") val participantId: Int = 0
)

@DatabaseView("SELECT * FROM balloon_table INNER JOIN bart_trial_data AS bartEntity WHERE bartEntity.participant_id = bart_participant_id")
data class BalloonView(
    val balloonNumber: Int,
    val maxInflations: Int,
    val numberOfInflations: Int,
    val didPop: Boolean,
    val participantId: Int
)