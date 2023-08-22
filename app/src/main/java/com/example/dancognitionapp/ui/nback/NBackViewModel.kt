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
import java.util.LinkedList

class NBackViewModel: ViewModel() {
    private val presentationOrders = mutableListOf(
        NBackGenerator(testType = NBackType.N_1).items,
        NBackGenerator(testType = NBackType.N_2).items,
        NBackGenerator(testType = NBackType.N_3).items
    )
    private var testType = 1
    private var lifetimePresentations = 0
    private val maxLifetimePresentations = 3 // number of presentations in a real test would be 9 else 3
    private val _uiState = mutableStateOf(
        NBackUiState(
            presentationList = presentationOrders[0]
        )
    )
    val uiState: State<NBackUiState> = _uiState

    private val currentState: NBackUiState
        get() = _uiState.value

    fun startAdvancing() {
        hideDialog()
        viewModelScope.launch {
            delay(2000)
            while (!currentState.presentationList.isEmpty()) {
                toNextItem()
                toggleScreenClickable()
                Timber.i("Current Char: ${uiState.value.currentItem} and list Size: ${uiState.value.presentationList.size}")
                delay(150) // Show Stimulus for 1500ms
                _uiState.value = currentState.copy(
                    currentItem = NBackItem.intermediateItem,
                    feedbackState = NBackFeedbackState.INTERMEDIATE
                )
                toggleScreenClickable()
                delay(50) // inter-stimulus time (no dot showing)
            }
            toNextList()
        }
    }
    private fun hideDialog() {
        _uiState.value = currentState.copy(
            showDialog = !currentState.showDialog
        )
    }

    fun participantClick(currentItem: NBackItem) {
        _uiState.value = currentState.copy(
            feedbackState = if(currentItem.isTarget()) NBackFeedbackState.HIT
                else NBackFeedbackState.FALSE_ALARM,
            hasUserClicked = true
        )
        Timber.i("User Clicked Screen")
    }
    private fun toggleScreenClickable() {
        _uiState.value = currentState.copy(
            isTestScreenClickable = !currentState.isTestScreenClickable,
            hasUserClicked = false
        )
    }

    private fun toNextItem() {
        if (currentState.presentationList.isEmpty()) {
            toNextList()
            return
        }
        _uiState.value = currentState.copy(
            presentationList = currentState.presentationList,
            currentItem = currentState.presentationList.pop()
        )
    }

    private fun toNextList() {
        lifetimePresentations++
        if (testType == 3 && lifetimePresentations < maxLifetimePresentations) {
            regeneratePresentationOrders()
            testType = 1
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[0],
                n = testType,
                showDialog = true
            )
        } else if (lifetimePresentations < maxLifetimePresentations) {
            testType += 1
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[testType - 1],
                showDialog = true,
                n = testType
            )
            Timber.i("${currentState.presentationList}")
        } else {
            _uiState.value = currentState.copy(
                isEndOfTest = true
            )
        }
    }
    private fun regeneratePresentationOrders() {
        presentationOrders[0] = NBackGenerator(testType = NBackType.N_1).items
        presentationOrders[1] = NBackGenerator(testType = NBackType.N_2).items
        presentationOrders[2] = NBackGenerator(testType = NBackType.N_3).items
    }
}