package com.example.dancognitionapp.assessment.dsst.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.dsst.ui.DsstIcons.ICONS
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class DsstScreenViewModel() : ViewModel() {

    private val TEST_DURATION = 120_000 // 2 minutes

    private val _uiState = MutableStateFlow(
        DsstUiState(
            bottomIcons = ICONS.shuffled(),
            stimulus = getNewStimulus()
        )
    )
    val uiState: StateFlow<DsstUiState> = _uiState


    private fun getNewStimulus() : Int {
        return Random.nextInt(1, ICONS.size + 1)
    }

    private fun updateStimulus() {
        var newStimulus = getNewStimulus()
        while (uiState.value.stimulus == newStimulus) {
            newStimulus = getNewStimulus()
        }
        _uiState.update {
            it.copy(
                stimulus = newStimulus
            )
        }
    }

    fun onAction(action: DsstAction) {
        when (action) {
            is DsstAction.KeyPressed -> handleKeyPressed(action.key)
        }
    }

    private fun handleKeyPressed(key: Int) {
        if (uiState.value.showCorrectFeedback || uiState.value.showIncorrectFeedback) {
            clearFeedback()
        }
        showFeedback(key == uiState.value.stimulus)
        updateStimulus()
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