package com.example.dancognitionapp.assessment.bart.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.assessment.bart.data.BalloonGenerator
import com.example.dancognitionapp.assessment.bart.data.BartUiState
import timber.log.Timber

class BartViewModel: ViewModel() {

    private val balloonList = BalloonGenerator().balloons

    /**
     * This first block sets up the ui state to be mutable and initializes the BalloonList
     * */
    private val _uiState = mutableStateOf(
        BartUiState(
            balloonList = balloonList
        )
    )
    val uiState: State<BartUiState> = _uiState
    private val currentState: BartUiState
        get() = _uiState.value
    fun inflateBalloon() {
        val canInflate =
            currentState.currentBalloon.maxInflations > currentState.currentInflationCount

        if (canInflate) {
            _uiState.value = currentState.copy(
                currentInflationCount = currentState.currentInflationCount.inc(),
                currentReward = currentState.currentReward.inc(),
                isBalloonPopped = false
            )
            Timber.i("Balloon Number ${currentState.currentBalloon.listPosition} was inflated!")
            Timber.d("\nInflations: ${currentState.currentInflationCount}" +
                    "\nmaxInflationCount: ${currentState.currentBalloon.maxInflations}" +
                    "\nCurrent Reward: ${currentState.currentReward}")
        } else {
            Timber.i(
                "Balloon Number ${currentState.currentBalloon.listPosition} popped!" +
                    " Max Inflation: ${currentState.currentBalloon.maxInflations} " +
                    " == Number of User Clicks ${currentState.currentInflationCount + 1}"
            )
            _uiState.value = currentState.copy(
                currentInflationCount = 0,
                currentReward = 1,
                isBalloonPopped = true
            )
            toNextBalloon()
        }
    }

    fun resetBalloonStatus() {
        _uiState.value = currentState.copy(
            isBalloonPopped = false
        )
    }

    fun hideDialog() {
        _uiState.value = currentState.copy(
            isBalloonPopped = false,
            isTestComplete = false
        )
    }

    fun collectBalloonReward() {
        _uiState.value = currentState.copy(
            totalEarnings = currentState.totalEarnings + currentState.currentReward,
            currentReward = 1,
            currentInflationCount = 0
        )
        Timber.i("Balloon Number ${currentState.currentBalloon.listPosition} collected!")
        toNextBalloon()
    }

    private fun toNextBalloon() {
        if (balloonList.isEmpty()) {
            _uiState.value = currentState.copy(
                isTestComplete = true
            )
            return
        }
        _uiState.value = currentState.copy(
            balloonList = balloonList,
            currentBalloon = balloonList.pop()
        )
    }
}