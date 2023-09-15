package com.example.dancognitionapp.assessment.bart.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime

@Entity(tableName = "bart_trials")
data class BartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("trial_day") val trialDay: TrialDay,
    @ColumnInfo("trial_time") val trialTime: TrialTime,
    @ColumnInfo("participant_id") val participantId: Int,
    @ColumnInfo("user_given_participant_id") val userGivenParticipantId: String,
)

@Entity(tableName = "balloon_table",
    foreignKeys = [
        ForeignKey(
            entity = BartEntity::class,
            parentColumns = ["id"],
            childColumns = ["bart_entity_id"]
        )
    ]
)
data class BalloonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("balloon_number") val balloonNumber: Int,
    @ColumnInfo("max_inflations") val maxInflations: Int,
    @ColumnInfo("number_of_inflations") val numberOfInflations: Int,
    @ColumnInfo("did_pop") val didPop: Boolean,
    @ColumnInfo("bart_entity_id") val bartEntityId: Int
)

@DatabaseView("""
    SELECT bart_trials.user_given_participant_id AS dan_participant_id, 
           bart_trials.trial_day, 
           bart_trials.trial_time,
           balloon_number,
           max_inflations,
           number_of_inflations,
           did_pop,
           bart_entity_id AS trial_id
    FROM balloon_table AS bart_trial_data
    INNER JOIN bart_trials
    ON bart_trials.id = bart_entity_id
""")
data class BartTrialData(
    @ColumnInfo("dan_participant_id") val userGivenParticipantId: String,
    @ColumnInfo("trial_day")val trialDay: TrialDay,
    @ColumnInfo("trial_time")val trialTime: TrialTime,
    @ColumnInfo("balloon_number")val balloonNumber: Int,
    @ColumnInfo("max_inflations")val maxInflations: Int,
    @ColumnInfo("number_of_inflations")val numberOfInflations: Int,
    @ColumnInfo("did_pop")val didPop: Boolean,
    @ColumnInfo("trial_id")val bartEntityId: Int
)