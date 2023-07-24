package com.example.dancognitionapp.bart.data

import java.util.LinkedList

data class BartUiState(
    val balloonList: LinkedList<Balloon>,
    val currentBalloon: Balloon = Balloon(1, 1),
    var balloonInflations: Int = 0,
    var balloonReward: Int = 1,
    var totalEarnings: Int = 0,
    var balloonPopped: Boolean = false
)
