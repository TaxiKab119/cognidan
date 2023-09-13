package com.example.dancognitionapp.participants.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "participants")
data class Participant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_inputted_id") val userGivenId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "notes") val notes: String

) : Parcelable {
    companion object {
        val emptyParticipant = Participant(0, "", "", "")
    }
}
