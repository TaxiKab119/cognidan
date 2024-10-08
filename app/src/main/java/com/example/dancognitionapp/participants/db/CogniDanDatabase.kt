package com.example.dancognitionapp.participants.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dancognitionapp.assessment.bart.db.BalloonEntity
import com.example.dancognitionapp.assessment.bart.db.BartDao
import com.example.dancognitionapp.assessment.bart.db.BartEntity
import com.example.dancognitionapp.assessment.bart.db.BartTrialData
import com.example.dancognitionapp.assessment.nback.db.NBackDao
import com.example.dancognitionapp.assessment.nback.db.NBackEntity
import com.example.dancognitionapp.assessment.nback.db.NBackItemEntity
import com.example.dancognitionapp.assessment.nback.db.NBackTrialData

@Database(
    entities = [Participant::class, BartEntity::class, BalloonEntity::class, NBackEntity::class, NBackItemEntity::class],
    views = [BartTrialData::class, NBackTrialData::class],
    version = 1,
    exportSchema = false
)
abstract class CogniDanDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao
    abstract fun bartDao() : BartDao
    abstract fun nBackDao(): NBackDao

    companion object {
        /**
         * The [Instance] variable keeps a reference to the database when one has been created.
         * This helps maintain a single instance of the database opened at a given time.
         * A database is an expensive resource to maintain.
         * */
        @Volatile // Value of a @Volatile variable is never cached, all reads and writes are to and from main memory
        private var Instance: CogniDanDatabase? = null
        fun getDatabase(context: Context): CogniDanDatabase {
            return Instance ?: synchronized(this) {
                // if the Instance is not null, return it, otherwise create a new database instance.
                Room.databaseBuilder(context, CogniDanDatabase::class.java, "cognidan_database")
                    .build()
                    .also { Instance = it } //keep a reference to the recently created db instance
            }
        }
    }
}