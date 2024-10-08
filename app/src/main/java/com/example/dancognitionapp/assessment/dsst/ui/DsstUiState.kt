package com.example.dancognitionapp.assessment.dsst.ui

data class DsstUiState(
    val showCorrectFeedback: Boolean = false,
    val showIncorrectFeedback: Boolean = false,
    val stimulus: Int = 0,
    val bottomIcons: List<Int>,
    val showDialog: Boolean = true,
    val testStarted: Boolean = false,
    val testEnded: Boolean = false,
    val isPractice: Boolean = false,
    val numberClicks: Int = 0,
    val numberCorrect: Int = 0,
    val numberIncorrect: Int = 0,
)
