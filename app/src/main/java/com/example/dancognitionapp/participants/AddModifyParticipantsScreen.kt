package com.example.dancognitionapp.participants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.participants
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    participant: Participant?,
    currentParticipantId: String = "",
    currentParticipantName: String = "",
    currentParticipantNotes: String = "",
    onParticipantIdChanged: (String) -> Unit = {},
    onParticipantNameChanged: (String) -> Unit = {},
    onParticipantNotesChanged: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            Column(modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = if (participant == null) "Add participant" else "Modify participant",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
                OutlinedTextField(
                    value = currentParticipantName,
                    onValueChange = { onParticipantNameChanged(it) },
                    label = { Text(text = "Participant name") },
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = currentParticipantId,
                    onValueChange = { onParticipantIdChanged(it) },
                    label = { Text(text = "Participant id") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                OutlinedTextField(
                    value = currentParticipantNotes,
                    onValueChange = { onParticipantNotesChanged(it) },
                    label = { Text(text = "Notes") },
                    singleLine = true,
                    //This is a hack and I don't know how to fix it better
//                    placeholder = { Text(text = "Notes\n\n\n") },
//                    maxLines = 3,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .clickable { onSaveClick() }
                ) { Text(text = "Save") }
            }
        }
    }
}

fun AddEditFullscreen(
    modifier: Modifier = Modifier,
    participant: Participant?,
    currentParticipantId: String = "",
    currentParticipantName: String = "",
    currentParticipantNotes: String = "",
    onParticipantIdChanged: (String) -> Unit = {},
    onParticipantNameChanged: (String) -> Unit = {},
    onParticipantNotesChanged: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {

}



@Preview(showBackground = true)
@Composable
fun AddEditParticipantPreview() {
    DanCognitionAppTheme {
        AddEditScreen(participant = participants.first())
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditParticipantFullScreenPreview() {
    DanCognitionAppTheme {
    }
}