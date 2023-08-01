package com.example.dancognitionapp.nback

import java.util.UUID

data class NBackItem(
    val id: UUID = UUID.randomUUID(),
    val position: Int,
    val letter: Char,
    val key: Char? = null

) {
    fun isTarget() = letter == key
}


enum class NBackKey {
    HIT,
    MISS,
    FALSE_ALARM,
    CORRECT_REJECTION
}