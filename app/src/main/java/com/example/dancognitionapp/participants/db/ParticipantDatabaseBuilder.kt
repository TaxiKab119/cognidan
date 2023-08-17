package com.example.dancognitionapp.participants.db

import android.content.Context
import androidx.room.Room

class ParticipantDatabaseBuilder() {
    companion object {
        fun createDb(context: Context) {
            Room.databaseBuilder(
                context = context,
                ParticipantDatabase::class.java, "participant-database"
            ).build()
        }
    }
}