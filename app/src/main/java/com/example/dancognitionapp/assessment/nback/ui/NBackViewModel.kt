package com.example.dancognitionapp.assessment.nback.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dancognitionapp.assessment.nback.data.NBackGenerator
import com.example.dancognitionapp.assessment.nback.data.NBackItem
import com.example.dancognitionapp.assessment.nback.data.NBackType
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class NBackViewModel(private val nBackRepository: NBackRepository): ViewModel() {
    private val presentationOrders = mutableListOf(
        NBackGenerator(testType = NBackType.N_1).items,
        NBackGenerator(testType = NBackType.N_2).items,
        NBackGenerator(testType = NBackType.N_3).items
    )
    private var testType = NBackType.N_1
    private var lifetimePresentations = 0
    private val maxLifetimePresentations = 3
    private val _uiState = MutableStateFlow(
        NBackUiState(
            presentationList = presentationOrders[0]
        )
    )
    val uiState: StateFlow<NBackUiState> = _uiState.asStateFlow()

    private val currentState: NBackUiState
        get() = _uiState.value

    fun startAdvancing() {
        hideDialog()
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            while (!currentState.presentationList.isEmpty()) {
                toNextItem()
                toggleScreenClickable()
                Timber.i("Current Char: ${uiState.value.currentItem} and list Size: ${uiState.value.presentationList.size}")

                val startShowingStimulusTime = System.currentTimeMillis()
                delay(1500) // Show Stimulus for 1500ms
                val clickTime = _uiState.value.clickTime
                var reactionTime: Long? = clickTime - startShowingStimulusTime
                if (clickTime == 0L) {
                    reactionTime = null
                }
                Timber.i("Reaction Time: $reactionTime")
                _uiState.value = currentState.copy(
                    currentItem = NBackItem.intermediateItem,
                    feedbackState = NBackFeedbackState.INTERMEDIATE,
                    clickTime = 0L
                )
                toggleScreenClickable()
                delay(500) // inter-stimulus time (no dot showing)
            }
            delay(1000) // add a delay before showing dialog for next type
            toNextList()
        }
    }
    private fun hideDialog() {
        _uiState.value = currentState.copy(
            showDialog = !currentState.showDialog
        )
    }

    fun participantClick(currentItem: NBackItem, clickTime: Long) {
        _uiState.value = currentState.copy(
            feedbackState = if(currentItem.isTarget()) NBackFeedbackState.HIT
                else NBackFeedbackState.FALSE_ALARM,
            hasUserClicked = true,
            clickTime = clickTime
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
        if (testType.value == 3 && lifetimePresentations < maxLifetimePresentations) {
            regeneratePresentationOrders()
            testType = NBackType.N_1
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[0],
                nValue = testType,
                showDialog = true
            )
        } else if (lifetimePresentations < maxLifetimePresentations) {
            testType = NBackType.values()[testType.ordinal + 1]
            _uiState.value = currentState.copy(
                presentationList = presentationOrders[testType.ordinal],
                showDialog = true,
                nValue = testType,
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