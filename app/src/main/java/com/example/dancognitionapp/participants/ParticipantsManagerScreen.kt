package com.example.dancognitionapp.participants


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.participants
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantManagerScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: ParticipantsViewModel = viewModel()
    val uiState by viewModel.uiState

    var showAddEdit by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val fabExtended by remember { derivedStateOf { !listState.isScrollInProgress } }
    Scaffold(
        topBar = {
            DanCognitionTopAppBar(headerResId = R.string.landing_participant_manager)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = fabExtended,
                onClick = {
                    viewModel.clearParticipantValues()
                    showAddEdit = !showAddEdit
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") },
                text = { Text(text = "Add participant")}
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            state = listState
        ) {
            items(uiState.participantList) {participant ->
                ParticipantCard(
                    participant = participant,
                    modifier = Modifier
                        .padding(8.dp)
                        .height(82.dp)
                ) {
                    viewModel.selectParticipant(it)
                    showAddEdit = true
                }
            }
        }
        if (showAddEdit) {
            ShowAddEditScreen(
                participant = uiState.selectedParticipant,
                onParticipantIdChanged =  { viewModel.addOrUpdateParticipantInfo(id = it) },
                onParticipantNameChanged = { viewModel.addOrUpdateParticipantInfo(name = it) },
                onParticipantNotesChanged = { viewModel.addOrUpdateParticipantInfo(notes = it) },
                currentParticipantId = uiState.currentParticipantId,
                currentParticipantName = uiState.currentParticipantName,
                currentParticipantNotes = uiState.currentParticipantNote,
                onSaveClick = {
                    if (uiState.selectedParticipant == null) {
                        viewModel.appendNewParticipant()
                        showAddEdit = false
                    } else {
                        viewModel.editExistingParticipant()
                        showAddEdit = false
                    }
                }
            ) {
                showAddEdit = false
                viewModel.clearParticipantValues()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParticipantCard(
    participant: Participant,
    modifier: Modifier = Modifier,
    onLongClick: (Participant) -> Unit = {}
) {
    Card(
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .combinedClickable(
                onClick = {},
                onLongClick = { onLongClick(participant) }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ParticipantIcon(
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp)
            )
            Text(
                text = participant.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun ParticipantIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Rounded.AccountCircle,
        contentDescription = null,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAddEditScreen(
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


@Preview(showBackground = true)
@Composable
fun ParticipantManagerScreenPreview() {
    DanCognitionAppTheme {
        ParticipantManagerScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantCardPreview() {
    DanCognitionAppTheme {
        ParticipantCard(participants.first())
    }
}

@Preview(showBackground = true)
@Composable
fun AddParticipantPreview() {
    DanCognitionAppTheme {
        ShowAddEditScreen(participant = participants.first())
    }
}