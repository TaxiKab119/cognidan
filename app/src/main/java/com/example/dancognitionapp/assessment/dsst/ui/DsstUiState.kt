package com.example.dancognitionapp.assessment.dsst.ui

data class DsstUiState(
    val showCorrectFeedback: Boolean = false,
    val showIncorrectFeedback: Boolean = false,
    val stimulus: Int = 0,
    val bottomIcons: List<Int>,
)
