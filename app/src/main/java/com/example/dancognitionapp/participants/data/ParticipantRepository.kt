package com.example.dancognitionapp.participants.data

import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Participant] from a given data source.
 */
interface ParticipantRepository {
    /**
     * Retrieve all the Participants from the the given data source.
     */
    fun getAllParticipantsStream(): Flow<List<Participant>>

    /**
     * Retrieve a Participant from the given data source that matches with the [id].
     */
    fun getParticipantByIdStream(id: Int): Flow<Participant?>

    /**
     * Insert Participant in the data source
     */
    suspend fun insertParticipant(participant: Participant)

    /**
     * Delete Participant from the data source
     */
    suspend fun deleteParticipant(participant: Participant)

    /**
     * Update Participant in the data source
     */
    suspend fun updateParticipant(participant: Participant)
}