package com.example.dancognitionapp.bart

import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.bart.data.BalloonGenerator
import com.example.dancognitionapp.bart.data.BartUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

class BartViewModel: ViewModel() {

    private var balloonList = BalloonGenerator().balloons

    /**
     * This first block sets up the ui state to be mutable and initializes the BalloonList
     * */
    private val _uiState = MutableStateFlow(
        BartUiState(
            balloonList = balloonList,
            currentBalloon = balloonList.first
        )
    )
    val uiState: StateFlow<BartUiState> = _uiState

    fun inflateBalloon() {
        val canInflate =
            _uiState.value.currentBalloon.maxInflations > _uiState.value.currentInflationCount + 1

        if(canInflate) {
            _uiState.update {
                it.copy(
                    currentInflationCount = it.currentInflationCount.inc(),
                    currentReward = it.currentReward.inc()
                )
            }
            Timber.i("Balloon Number ${_uiState.value.currentBalloon.listPosition} was inflated!")
        } else {
            Timber.i(
                "Balloon Number ${_uiState.value.currentBalloon.listPosition} popped!" +
                    " Max Inflation: ${_uiState.value.currentBalloon.maxInflations} " +
                    " == Number of User Clicks ${_uiState.value.currentInflationCount + 1}"
            )
            _uiState.update {
                it.copy(
                    currentInflationCount = 0,
                    currentReward = 1,
                    balloonPopped = true
                )
            }
            toNextBalloon()
        }
    }

    fun resetBalloonStatus() {
        _uiState.update {
            it.copy(
                balloonPopped = false
            )
        }
    }

    fun collectBalloonReward(reward: Int) {
        _uiState.update {
            it.copy(
                totalEarnings = it.totalEarnings + reward,
                currentReward = 1,
                currentInflationCount = 0
            )
        }
        Timber.i("Balloon Number ${_uiState.value.currentBalloon.listPosition} collected!")
        toNextBalloon()
    }

    private fun toNextBalloon() {
        val isBalloonListEmpty: Boolean = _uiState.value.balloonList.size == 1

        if (isBalloonListEmpty) {
            /*TODO*/
            // have something to show test is complete
            Timber.i("BART completed!")
        } else {
            _uiState.value.balloonList.removeFirst()
            _uiState.update {
                it.copy(
                    balloonList = balloonList,
                    currentBalloon = balloonList.first
                )
            }

        }

    }

}