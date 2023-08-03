package com.example.dancognitionapp.participants

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dancognitionapp.participants.data.Participant

@Composable
fun AddModifyParticipantsScreen(
    participant: Participant? = null,
    modifier: Modifier = Modifier,
    isModify: Boolean = false
) {
    if (isModify) {
        Text(text = participant?.name ?: "Error")
    } else {
        Text(text = "Add participant")
    }
}

@Composable
fun AddModifyParticipantsContent() {

}