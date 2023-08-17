package com.example.dancognitionapp.participants.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Participant::class], version = 1)
abstract class ParticipantDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao
}