package com.example.dancognitionapp.assessment.dsst.ui

sealed class DsstAction {
    data class KeyPressed(val key: Int, val currStimulus: Int) : DsstAction()
    object OkClickedDialog : DsstAction()
    object CancelClickedDialog : DsstAction()
}