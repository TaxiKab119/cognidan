package com.example.dancognitionapp.assessment.nback.data

data class NBackItem(
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

    companion object {
        val intermediateItem = NBackItem(
            position = -1,
            letter = '0'
        )
    }
}