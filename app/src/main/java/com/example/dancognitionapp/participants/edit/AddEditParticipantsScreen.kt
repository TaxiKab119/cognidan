package com.example.dancognitionapp.participants.edit

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ParticipantScreenType() {
    ADD, EDIT
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditParticipantsFullScreen(
    modifier: Modifier = Modifier,
    screenType: ParticipantScreenType,
    viewModel: AddEditViewModel,
    uiState: AddEditUiState,
    onConfirm: () -> Unit = {},
    returnToManager: () -> Unit = {}
) {

    var showDeleteDialog: Boolean by remember { mutableStateOf(false) }
    var showCloseDialog: Boolean by remember { mutableStateOf(false) }
    var showLoadingDialog: Boolean by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope() // need this to call updateItem() function

    lateinit var initialUiState: AddEditUiState // This only gets initialized if haveFieldsBeenPopulated is a uiState
    if (uiState.haveFieldsBeenPopulated) {
        initialUiState = remember { uiState }
        Timber.i("InitialUiState updated to: $initialUiState")
    }
    if (showCloseDialog) {
        ParticipantDialog(
            title = R.string.participants_close_dialog_title,
            content = R.string.participants_close_dialog_content,
            onConfirm = {
                returnToManager()
            }
        ) { showCloseDialog = false }
    }
    if (showDeleteDialog) {
        ParticipantDialog(
            title = R.string.participants_delete_dialog_title,
            content = R.string.participants_delete_dialog_content,
            onConfirm = {
                showDeleteDialog = false
                onConfirm()
            }
        ) { showDeleteDialog = false }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AddEditFullScreenHeader(
            screenType = screenType,
            modifier = Modifier.fillMaxWidth(),
            onClose = {
                Timber.i("initialUiState: $initialUiState vs. uiState: $uiState")
                if (initialUiState != uiState) {
                    showCloseDialog = true
                } else {
                    returnToManager()
                }
            }
        ) {
            when(screenType) {
                ParticipantScreenType.ADD -> {
                    coroutineScope.launch {
                        viewModel.saveNewParticipant()
                    }
                }
                ParticipantScreenType.EDIT -> {
                    coroutineScope.launch {
                        viewModel.updateParticipant()
                    }
                }
            }
            returnToManager()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            val updatedState by remember(uiState) {
                mutableStateOf(uiState)
            }
            OutlinedTextField(
                value = updatedState.participantDetails.name,
                onValueChange = { viewModel.updateUiState(participantDetails = uiState.participantDetails.copy(name = it)) },
                label = { Text(text = stringResource(R.string.participants_participant_name_label)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = updatedState.participantDetails.userGivenId,
                onValueChange = { viewModel.updateUiState(participantDetails = uiState.participantDetails.copy(userGivenId = it)) },
                label = { Text(text = stringResource(R.string.participants_participant_id_label)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = updatedState.participantDetails.notes,
                onValueChange = { viewModel.updateUiState(participantDetails = uiState.participantDetails.copy(notes = it)) },
                label = { Text(text = stringResource(R.string.participants_notes_label)) },
                singleLine = false,
                maxLines = 4,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
            if (screenType == ParticipantScreenType.EDIT) {
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
    screenType: ParticipantScreenType,
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
            text = when (screenType) {
                ParticipantScreenType.ADD -> stringResource(R.string.participants_add_participant_title)
                ParticipantScreenType.EDIT -> stringResource(R.string.participants_modify_participant_title)
            },
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
            ) {
                Text(stringResource(R.string.ok_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onCancel() },
            ) {
                Text(stringResource(R.string.cancel_button))
            }
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