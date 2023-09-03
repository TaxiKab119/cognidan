package com.example.dancognitionapp.assessment.nback

import com.example.dancognitionapp.assessment.nback.data.NBackItem
import com.example.dancognitionapp.assessment.nback.data.NBackType
import java.util.LinkedList

data class NBackUiState(
    val presentationList: LinkedList<NBackItem>,
    val currentItem: NBackItem = NBackItem.intermediateItem,
    val feedbackState: NBackFeedbackState = NBackFeedbackState.INTERMEDIATE,
    val isTestScreenClickable: Boolean = false,
    val hasUserClicked: Boolean = false,
    val showDialog: Boolean = true,
    val nValue: NBackType = NBackType.N_1,
    val isEndOfTest: Boolean = false
)
