package com.example.dancognitionapp.participants.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Participant(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "user_inputted_id") val userGivenId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "notes") val notes: String

)
