package com.example.dancognitionapp.participants

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.participants
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantManagerScreen(
    modifier: Modifier = Modifier,
    onClickParticipant: (Int?) -> Unit = {},
    onAdd: () -> Unit = {}
) {
    var selectedParticipantId: Int? by remember { mutableStateOf(null) }
    Scaffold(
        topBar = {
            DanCognitionTopAppBar(headerResId = R.string.landing_participant_manager)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedParticipantId != null) {
                        onClickParticipant(selectedParticipantId)
                    } else {
                        onAdd()
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(76.dp)
            ) {
                if (selectedParticipantId != null) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                } else {
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            }
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(participants) {participant ->
                val isSelected = selectedParticipantId == participant.id
                ParticipantCard(
                    participant = participant,
                    modifier = Modifier.padding(8.dp),
                    isSelected = isSelected
                ) { participantId ->
                    if (isSelected) {
                        /**Allow for deselection*/
                        Timber.i("Participant $participant de-selected")
                        selectedParticipantId = null
                    } else {
                        selectedParticipantId = participantId
                        onClickParticipant(participantId)
                        Timber.i("Participant $participant selected")
                    }
                }
            }
        }
    }
}

@Composable
fun ParticipantCard(
    participant: Participant,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (Int?) -> Unit = {}
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer
    )
    Card(modifier = modifier.height(82.dp)) {
        Column(modifier = Modifier
            .background(color = color)
            .fillMaxSize()
            .clickable { onClick(participant.id) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                ParticipantIcon()
                ParticipantInformation(participant.name)
            }
        }
    }
}

@Composable
fun ParticipantIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Rounded.AccountCircle,
        contentDescription = null,
        modifier = Modifier
            .size(72.dp)
            .padding(8.dp)
    )
}

@Composable
fun ParticipantInformation(
    participantName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = participantName,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(8.dp),
        overflow = TextOverflow.Ellipsis
    )
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