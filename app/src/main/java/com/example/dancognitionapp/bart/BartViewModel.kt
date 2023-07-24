package com.example.dancognitionapp.bart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.bart.data.BalloonGenerator
import com.example.dancognitionapp.bart.data.BartUiState
import timber.log.Timber

class BartViewModel: ViewModel() {

    private val balloonList = BalloonGenerator().balloons

    /**
     * This first block sets up the ui state to be mutable and initializes the BalloonList
     * */
    private val _uiState = mutableStateOf(
        BartUiState(
            balloonList = balloonList,
            currentBalloon = balloonList.first
        )
    )
    val uiState: State<BartUiState> = _uiState
    private val currentState: BartUiState
        get() = _uiState.value
    fun inflateBalloon() {
        val isListEmpty: Boolean = balloonList.isEmpty()
        if (isListEmpty) {
            Timber.i("BART completed!")
            return
        }
        val canInflate =
            currentState.currentBalloon.maxInflations > currentState.currentInflationCount

        if (canInflate) {
            val updatedState = currentState.copy(
                currentInflationCount = currentState.currentInflationCount.inc(),
                currentReward = currentState.currentReward.inc()
            )
            _uiState.value = updatedState
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
            val updatedState = currentState.copy(
                currentInflationCount = 0,
                currentReward = 1,
                balloonPopped = true
            )
            _uiState.value = updatedState
            toNextBalloon()
        }
    }

    fun resetBalloonStatus() {
        val updatedState = currentState.copy(
            balloonPopped = false
        )
        _uiState.value = updatedState
        Timber.i("balloonPopped: ${_uiState.value.balloonPopped}")
    }

    fun collectBalloonReward() {
        val isListEmpty: Boolean = balloonList.isEmpty()
        if (isListEmpty) {
            Timber.i("BART completed!")
            return
        }
        val updatedState = currentState.copy(
            totalEarnings = currentState.totalEarnings + currentState.currentReward,
            currentReward = 1,
            currentInflationCount = 0
        )
        _uiState.value = updatedState
        Timber.i("Balloon Number ${currentState.currentBalloon.listPosition} collected!")
        toNextBalloon()
    }

    private fun toNextBalloon() {
        val isLastBalloon: Boolean = currentState.balloonList.size == 1

        if (isLastBalloon) {
            /*TODO*/
            // have something to show test is complete
            // disable buttons or go to completed screen?
            Timber.i("BART completed!")
        } else {
            currentState.balloonList.removeFirst()
            _uiState.value = currentState.copy(
                balloonList = balloonList,
                currentBalloon = balloonList.first
            )
        }
    }
}