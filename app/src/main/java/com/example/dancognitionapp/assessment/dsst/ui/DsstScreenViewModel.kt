package com.example.dancognitionapp.assessment.dsst.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.dsst.ui.DsstIcons.DSST_ICONS
import com.example.dancognitionapp.participants.db.Participant
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class DsstScreenViewModel(isPractice: Boolean) : ViewModel() {

    private val TEST_DURATION: Long = if (isPractice) 60_000L else 120_000L // 2 minutes

    private var numClicks: Int = 0
    private var numCorrect: Int = 0
    private var numIncorrect: Int = 0

    private val _uiState = MutableStateFlow(
        DsstUiState(
            bottomIcons = DSST_ICONS.shuffled(),
            stimulus = 0,
            isPractice = isPractice
        )
    )
    val uiState: StateFlow<DsstUiState> = _uiState


    fun initDsst(participant: Participant, trialDay: TrialDay, trialTime: TrialTime) {
        // TODO - This is where the db logic will go
        // see BART and NBACK for inspo
    }


    private fun getNewStimulus() : Int {
        return Random.nextInt(1, DSST_ICONS.size + 1)
    }

    private fun updateStimulus() {
        _uiState.update {
            it.copy(
                stimulus = getNewStimulus()
            )
        }
    }

    private fun startTest() {
        _uiState.update {
            it.copy(
                showDialog = false
            )
        }
        viewModelScope.launch {
            delay(2000) // Before stimuli show
            _uiState.update { it.copy(testStarted = true, stimulus = getNewStimulus()) }
            delay(TEST_DURATION)
            updateParticipantScores()
            _uiState.update { it.copy(testEnded = true, showDialog = true) }
        }
    }

    fun onAction(action: DsstAction) {
        when (action) {
            is DsstAction.KeyPressed -> {
                if (uiState.value.testStarted) handleKeyPressed(action.key, action.currStimulus)
            }
            DsstAction.OkClickedDialog -> startTest()
            DsstAction.CancelClickedDialog -> {/* Handled in Fragment - Navigates back*/}
        }
    }

    private fun handleKeyPressed(key: Int, stimulus: Int) {
        if (uiState.value.showCorrectFeedback || uiState.value.showIncorrectFeedback) {
            clearFeedback()
        }
        val isCorrect = key == DSST_ICONS[stimulus - 1]
        showFeedback(isCorrect)
        numClicks++
        if (isCorrect) numCorrect++ else numIncorrect++
        updateStimulus()
    }

    private fun updateParticipantScores() {
        _uiState.update { it.copy(
            numberCorrect = numCorrect,
            numberClicks = numClicks,
            numberIncorrect = numIncorrect
        ) }
    }

    private fun clearFeedback() {
        _uiState.update { currentState ->
            currentState.copy(
                showCorrectFeedback = false,
                showIncorrectFeedback = false
            )
        }
    }


    private var feedbackJob: Job? = null

    private fun showFeedback(isCorrect: Boolean) {
        feedbackJob?.cancel() // Cancel any existing job
        if (isCorrect) {
            _uiState.update { currentState ->
                currentState.copy(
                    showCorrectFeedback = true,
                    showIncorrectFeedback = false
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    showCorrectFeedback = false,
                    showIncorrectFeedback = true
                )
            }
        }
        feedbackJob = viewModelScope.launch {
            delay(1000) // Adjust the delay as needed
            _uiState.update { currentState ->
                currentState.copy(
                    showCorrectFeedback = false,
                    showIncorrectFeedback = false
                )
            }
        }
    }
}