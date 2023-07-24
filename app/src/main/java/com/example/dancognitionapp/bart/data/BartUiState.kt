package com.example.dancognitionapp.bart.data

import java.util.LinkedList

data class BartUiState(
    val balloonList: LinkedList<Balloon>,
    val currentBalloon: Balloon = balloonList.first,
    var currentInflationCount: Int = 0,
    var currentReward: Int = 1,
    var totalEarnings: Int = 0,
    var balloonPopped: Boolean = false
)
