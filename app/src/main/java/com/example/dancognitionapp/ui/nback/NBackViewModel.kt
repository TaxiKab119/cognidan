package com.example.dancognitionapp.ui.nback

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dancognitionapp.nback.NBackGenerator
import com.example.dancognitionapp.nback.NBackType

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

    fun toNextItem() {
        if (presentationList.isEmpty()) {
            return
        }
        _uiState.value = currentState.copy(
            presentationList = presentationList,
            currentItem = presentationList.pop()
        )
    }
}