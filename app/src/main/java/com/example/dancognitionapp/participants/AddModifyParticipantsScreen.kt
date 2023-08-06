package com.example.dancognitionapp.participants

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.dancognitionapp.R
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditFullScreenContent(
    participant: Participant?,
    modifier: Modifier = Modifier,
    currentParticipantId: String = "",
    currentParticipantName: String = "",
    currentParticipantNotes: String = "",
    onParticipantIdChanged: (String) -> Unit = {},
    onParticipantNameChanged: (String) -> Unit = {},
    onParticipantNotesChanged: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    var showDeleteDialog: Boolean by remember { mutableStateOf(false) }
    var showCloseDialog: Boolean by remember { mutableStateOf(false) }

    if (showCloseDialog) {
        ParticipantDialog(
            title = R.string.participants_close_dialog_title,
            content = R.string.participants_close_dialog_content,
            onConfirm = { onClose() }
        ) { showCloseDialog = false }
    }
    if (showDeleteDialog) {
        ParticipantDialog(
            title = R.string.participants_delete_dialog_title,
            content = R.string.participants_delete_dialog_content,
            onConfirm = { onSaveClick() }
        ) { showDeleteDialog = false }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AddEditFullScreenHeader(
            participant = participant,
            modifier = Modifier.fillMaxWidth(),
            onClose = { showCloseDialog = true }
        ) { onSaveClick() }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = currentParticipantName,
                onValueChange = { onParticipantNameChanged(it) },
                label = { Text(text = stringResource(R.string.participants_participant_name_label)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = currentParticipantId,
                onValueChange = { onParticipantIdChanged(it) },
                label = { Text(text = stringResource(R.string.participants_participant_id_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = currentParticipantNotes,
                onValueChange = { onParticipantNotesChanged(it) },
                label = { Text(text = stringResource(R.string.participants_notes_label)) },
                singleLine = false,
                maxLines = 4,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            if (participant != null) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Text(stringResource(R.string.participants_delete_participant))
                }
            }
        }
    }
}

@Composable
fun AddEditFullScreenHeader(
    participant: Participant?,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onSave: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(start = 16.dp, end = 24.dp, top = 24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(36.dp)
                .padding(end = 16.dp)
                .fillMaxWidth()
                .clickable { onClose() }
        )
        Text(
            text = if (participant == null) stringResource(R.string.participants_add_participant_title)
                else stringResource(R.string.participants_modify_participant_title),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.weight(1f))
        TextButton(onClick = { onSave() }) { Text(text = "Save") }
    }
}

@Composable
fun ParticipantDialog(
    @StringRes title: Int,
    @StringRes content: Int,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) { Text(stringResource(R.string.ok_button)) }
        },
        dismissButton = {
            TextButton(
                onClick = { onCancel() },
            ) { Text(stringResource(R.string.cancel_button)) }
        },
        title = { Text(text = stringResource(title))},
        text = { Text(text = stringResource(content))}
    )
}
@Preview(showBackground = true)
@Composable
fun CloseDialogPreview() {
    DanCognitionAppTheme {
        ParticipantDialog(
            title = R.string.participants_close_dialog_title,
            content = R.string.participants_close_dialog_content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditParticipantFullScreenPreview() {
    DanCognitionAppTheme {
        AddEditFullScreenContent(participant = participants.first(), modifier = Modifier.fillMaxSize())
    }
}