package com.example.dancognitionapp.assessment.dsst.ui

sealed class DsstAction {
    data class KeyPressed(val key: Int) : DsstAction()
}