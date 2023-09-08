package com.example.dancognitionapp.participants.seetrialdata

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartEntity
import com.example.dancognitionapp.assessment.nback.db.NBackEntity
import com.example.dancognitionapp.landing.DanCognitionTopAppBar

enum class TestType{
    BART,
    NBACK
}

data class TrialDataFields(
    val danParticipantId: String,
    val participantId: Int,
    val participantName: String,
    val trialDay: TrialDay,
    val trialTime: TrialTime,
    val testType: TestType,
    val trialId: Int
)
@Composable
fun ParticipantsTrialDataScreen(
    uiState: ParticipantsTrialDataUiState,
    viewModel: ParticipantsTrialDataViewModel,
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit = {}
) {
    Scaffold(
        topBar = { DanCognitionTopAppBar(headerResId = R.string.participants_data_header)},
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { onFabClick() }) {
                Text(text = "Export")
                Icon(imageVector = Icons.Default.Send, contentDescription = "Export to .csv")
            }
        }
    ) {
        Column(modifier = modifier.padding(it)) {
            ParticipantDataTopCard(
                participantName = uiState.selectedParticipant.name,
                participantId = uiState.selectedParticipant.userGivenId,
                participantNotes = uiState.selectedParticipant.notes
            )
            CollapsibleParticipantDataItemsGroups(
                title = "BART Data",
                items = mapToTrialDataFields(
                    name = uiState.selectedParticipant.name,
                    bartEntities = uiState.allBartTrials
                ),
                onDeleteClicked = {},
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {

            }
            CollapsibleParticipantDataItemsGroups(
                title = "NBACK Data",
                items = mapToTrialDataFields(
                    name = uiState.selectedParticipant.name,
                    nBackEntities = uiState.allNBackTrials
                ),
                onDeleteClicked = {},
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {

            }
        }

    }
}

//@Composable
//fun ParticipantDataItem(
//    trialId: Int,
//    trialDay: TrialDay,
//    trialTime: TrialTime,
//    modifier: Modifier = Modifier,
//    onDeleteClicked: (Int) -> Unit = {},
//    onCheckBoxClick: (Int) -> Unit = {},
//) {
//    var isChecked by remember { mutableStateOf(true) }
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(top = 12.dp, start = 0.dp, bottom = 12.dp, end = 12.dp)
//    ) {
//        Row(
//            modifier = modifier
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            Checkbox(
//                checked = isChecked,
//                onCheckedChange = {
//                    isChecked = it
//                    onCheckBoxClick(trialId)
//                }
//            )
//            Text(text = "Day: ${trialDay.name}")
//            Spacer(modifier = Modifier.width(24.dp))
//            Text(text = "Time: ${trialTime.name}")
//            Spacer(modifier = Modifier.weight(1f))
//            IconButton(onClick = { onDeleteClicked(trialId) }) {
//                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
//            }
//        }
//    }
//}
@Composable
fun ParticipantDataTopCard(
    participantName: String,
    participantId: String,
    participantNotes: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text =
                stringResource(
                    id = R.string.participants_participant_name_label) + ": "
                        + participantName
            )
            Text(
                text = stringResource(
                    id = R.string.participants_participant_id_label) + ": "
                        + participantId
            )
            if (participantNotes != "") {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = participantNotes)
            }
        }
    }
}

@Composable
fun CollapsibleParticipantDataItemsGroups(
    title: String,
    items: List<TrialDataFields>,
    modifier: Modifier = Modifier,
    onDeleteClicked: (Int) -> Unit = {},
    onCheckBoxClick: (Int) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.clickable { isExpanded = !isExpanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "Expand",
                modifier = Modifier.padding(8.dp)
            )
        }
        if (isExpanded) {
            if (items.isEmpty()) {
                Text(text = stringResource(id = R.string.participants_data_no_trials_message))
            } else {
                LazyColumn {
                    items(items) {trialData ->
                        ParticipantDataItem(
                            participantDataFields = trialData,
                            onDeleteClicked = { onDeleteClicked(it) },
                        ) {
                            onCheckBoxClick(it)
                        }
                    }
                }
            }

        }
    }
}
private fun mapToTrialDataFields(
    name: String,
    nBackEntities: List<NBackEntity>? = null,
    bartEntities: List<BartEntity>? = null
): List<TrialDataFields> {
    if (nBackEntities == null && bartEntities != null) {
        return bartEntities.map {
            TrialDataFields(
                danParticipantId = it.userGivenParticipantId,
                participantId = it.participantId,
                trialDay = it.trialDay,
                trialTime = it.trialTime,
                testType = TestType.BART,
                trialId = it.id,
                participantName = name
            )
        }
    } else if (nBackEntities != null && bartEntities == null) {
        return nBackEntities.map {
            TrialDataFields(
                danParticipantId = it.userGivenParticipantId,
                participantId = it.participantId,
                trialDay = it.trialDay,
                trialTime = it.trialTime,
                testType = TestType.NBACK,
                trialId = it.id,
                participantName = name
            )
        }
    } else {
        return listOf()
    }
}
@Composable
fun ParticipantDataItem(
    participantDataFields: TrialDataFields,
    modifier: Modifier = Modifier,
    onDeleteClicked: (Int) -> Unit = {},
    onCheckBoxClick: (Int) -> Unit = {},
) {
    var isChecked by remember { mutableStateOf(true) }
    ListItem(
        headlineContent = { Text(text = participantDataFields.trialTime.name) },
        overlineContent = {
            Text(
                text = "${participantDataFields.participantName}'s " +
                        "(id: ${participantDataFields.danParticipantId}}) " +
                        "${participantDataFields.testType.name} data"
            )
        },
        supportingContent = { Text(text = participantDataFields.trialDay.name) },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            leadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
            supportingColor = MaterialTheme.colorScheme.onPrimaryContainer,
            trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        leadingContent = {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckBoxClick(participantDataFields.trialId)
                }
            )
        },
        trailingContent = {
            IconButton(onClick = { onDeleteClicked(participantDataFields.trialId) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

