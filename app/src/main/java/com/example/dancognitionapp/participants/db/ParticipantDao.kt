package com.example.dancognitionapp.participants.db

import android.content.ClipData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participants")
    fun getAllParticipants(): Flow<List<Participant>>

    @Query("SELECT * FROM participants WHERE id = :id")
    fun findParticipantById(id: Int): Flow<Participant>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertParticipant(participant: Participant)

    @Update
    suspend fun updateParticipant(participant: Participant)

    @Delete
    suspend fun deleteParticipant(participant: Participant)
}