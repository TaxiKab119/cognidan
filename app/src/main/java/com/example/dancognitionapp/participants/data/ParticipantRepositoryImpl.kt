package com.example.dancognitionapp.participants.data

import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.participants.db.ParticipantDao
import kotlinx.coroutines.flow.Flow

class ParticipantRepositoryImpl(private val participantDao: ParticipantDao) : ParticipantRepository {

    override fun getAllParticipantsStream(): Flow<List<Participant>> {
        return participantDao.getAllParticipants()
    }

    override fun getParticipantByIdStream(id: Int): Flow<Participant> {
        return participantDao.findParticipantById(id)
    }

    override suspend fun insertParticipant(participant: Participant) {
        return participantDao.insertParticipant(participant)
    }

    override suspend fun deleteParticipant(participant: Participant) {
        return participantDao.deleteParticipant(participant)
    }

    override suspend fun updateParticipant(participant: Participant) {
        return participantDao.updateParticipant(participant)
    }
}