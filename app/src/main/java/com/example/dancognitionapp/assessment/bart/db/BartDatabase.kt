package com.example.dancognitionapp.assessment.bart.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BartEntity::class, BalloonEntity::class], views = [BalloonView::class], version = 1, exportSchema = false)
abstract class BartDatabase : RoomDatabase() {
    abstract fun bartDao(): BartDao

    companion object {
        /**
         * The [Instance] variable keeps a reference to the database when one has been created.
         * This helps maintain a single instance of the database opened at a given time.
         * A database is an expensive resource to maintain.
         * */
        @Volatile // Value of a @Volatile variable is never cached, all reads and writes are to and from main memory
        private var Instance: BartDatabase? = null
        fun getDatabase(context: Context): BartDatabase {
            return Instance ?: synchronized(this) {
                // if the Instance is not null, return it, otherwise create a new database instance.
                Room.databaseBuilder(context, BartDatabase::class.java, "bart_database")
                    .fallbackToDestructiveMigration() // SHOULD MAYBE HAVE A BETTER MIGRATION STRAT?
                    .build()
                    .also { Instance = it } //keep a reference to the recently created db instance
            }
        }
    }
}