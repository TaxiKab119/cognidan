package com.example.dancognitionapp.ui.nback

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.nback.NBackGenerator
import com.example.dancognitionapp.nback.NBackItem
import com.example.dancognitionapp.nback.NBackType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class NBackViewModel: ViewModel() {
    private val presentationList = NBackGenerator(testType = NBackType.N_1).items

    private val _uiState = mutableStateOf(
        NBackUiState(
            presentationList = presentationList
        )
    )
    val uiState: State<NBackUiState> = _uiState

    private val currentState: NBackUiState
        get() = _uiState.value

    fun startAdvancing() {
        viewModelScope.launch {
            delay(2000)
            while (!presentationList.isEmpty()) {
                toNextItem()
                Timber.i("Current Char: ${uiState.value.currentItem} and list Size: ${uiState.value.presentationList.size}")
                delay(1500) // Show Stimulus for 1500ms
                _uiState.value = currentState.copy(
                    currentItem = NBackItem.intermediateItem,
                    feedbackState = NBackFeedbackState.INTERMEDIATE
                )
                delay(500) // inter-stimulus time (no dot showing)
            }
        }
    }

    fun participantClick(currentItem: NBackItem) {
        _uiState.value = currentState.copy(
            feedbackState = if(currentItem.isTarget()) NBackFeedbackState.HIT
                else NBackFeedbackState.FALSE_ALARM
        )
    }

    private fun toNextItem() {
        if (presentationList.isEmpty()) {
            return
        }
        _uiState.value = currentState.copy(
            presentationList = presentationList,
            currentItem = presentationList.pop()
        )
    }
}