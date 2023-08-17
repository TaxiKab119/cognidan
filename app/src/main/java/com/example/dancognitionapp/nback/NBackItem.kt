package com.example.dancognitionapp.nback

import java.util.UUID

data class NBackItem(
    val id: UUID = UUID.randomUUID(),
    val position: Int,
    val letter: Char,
    val key: Char? = null,
    val plusLurePositionChar: Char? = null, // Char at index of key + 1
    val minusLurePositionChar: Char? = null // Char at index of key - 1
) {
    fun isTarget() = letter == key
    fun hasLure(): Boolean {
        return (plusLurePositionChar != null && plusLurePositionChar == letter) ||
                (minusLurePositionChar != null && minusLurePositionChar == letter)
    }
}


enum class NBackKey {
    HIT,
    MISS,
    FALSE_ALARM,
    CORRECT_REJECTION
}