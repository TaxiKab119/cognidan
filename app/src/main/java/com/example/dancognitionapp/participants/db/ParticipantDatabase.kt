package com.example.dancognitionapp.participants.db

import android.content.Context
import android.provider.Telephony.Mms.Part
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Participant::class], version = 1, exportSchema = false)
abstract class ParticipantDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao

    companion object {
        /**
         * The [Instance] variable keeps a reference to the database when one has been created.
         * This helps maintain a single instance of the database opened at a given time.
         * A database is an expensive resource to maintain.
         * */
        @Volatile // Value of a @Volatile variable is never cached, all reads and writes are to and from main memory
        private var Instance: ParticipantDatabase? = null
        fun getDatabase(context: Context): ParticipantDatabase {
            return Instance ?: synchronized(this) {
                // if the Instance is not null, return it, otherwise create a new database instance.
                Room.databaseBuilder(context, ParticipantDatabase::class.java, "participant_database")
                    .fallbackToDestructiveMigration() // SHOULD MAYBE HAVE A BETTER MIGRATION STRAT?
                    .build()
                    .also { Instance = it } //keep a reference to the recently created db instance
            }
        }
    }
}