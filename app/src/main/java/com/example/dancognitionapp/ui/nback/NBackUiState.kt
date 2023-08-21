package com.example.dancognitionapp.ui.nback

import com.example.dancognitionapp.nback.NBackItem
import java.util.LinkedList

data class NBackUiState(
    val presentationList: LinkedList<NBackItem>,
    val currentItem: NBackItem = NBackItem.intermediateItem,
    val feedbackState: NBackFeedbackState = NBackFeedbackState.INTERMEDIATE,
    val isTestScreenClickable: Boolean = false,
    val hasUserClicked: Boolean = false
)
