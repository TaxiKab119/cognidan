package com.example.dancognitionapp.participants.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participant")
    fun getAll(): List<Participant>

    @Query("SELECT * FROM participant WHERE id == (:id)")
    fun findById(id: Int): Participant

    @Insert
    fun insertAll(vararg users: Participant)

    @Delete
    fun delete(participant: Participant)
}