package com.example.dancognitionapp.nback

data class NBackItem(
    val id: Int,
    val listPosition: Int,
    val letter: String,
    val key: NBackKey
)


enum class NBackKey {
    HIT,
    MISS,
    FALSE_ALARM,
    CORRECT_REJECTION
}