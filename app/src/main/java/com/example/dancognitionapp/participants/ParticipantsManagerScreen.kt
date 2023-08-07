package com.example.dancognitionapp.participants


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantUiState
import com.example.dancognitionapp.participants.data.participants
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantManagerScreen(
    modifier: Modifier = Modifier,
    viewModel: ParticipantsViewModel,
    uiState: ParticipantUiState,
    goToAddEditScreen: () -> Unit = {}
) {
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
                    viewModel.setAddScreen()
                    goToAddEditScreen()
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
                    goToAddEditScreen()
                }
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
                    .size(48.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = participant.name,
                    style = MaterialTheme.typography.headlineSmall,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "ID: ${participant.id}",
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis
                )
            }

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

@Preview(showBackground = true)
@Composable
fun ParticipantManagerScreenPreview() {
    DanCognitionAppTheme {
        val viewModel: ParticipantsViewModel = viewModel()
        val uiState: ParticipantUiState by viewModel.uiState
        ParticipantManagerScreen(
            viewModel = viewModel,
            uiState = uiState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantCardPreview() {
    DanCognitionAppTheme {
        ParticipantCard(participants.first())
    }
}