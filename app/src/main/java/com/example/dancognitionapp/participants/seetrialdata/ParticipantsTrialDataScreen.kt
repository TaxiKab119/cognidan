package com.example.dancognitionapp.participants.seetrialdata

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartEntity
import com.example.dancognitionapp.assessment.nback.db.NBackEntity
import com.example.dancognitionapp.landing.DanMenuItem
import com.example.dancognitionapp.participants.edit.ParticipantDialog
import com.example.dancognitionapp.utils.widget.DanCognitionTopAppBar

enum class TestType {
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

data class TrialIdentifier(
    val trialId: Int,
    val testType: TestType
) {
    companion object {
        val emptyTrialId = TrialIdentifier(0, TestType.BART)
    }
}

@Composable
fun ParticipantsTrialDataScreen(
    uiState: ParticipantsTrialDataUiState,
    viewModel: ParticipantsTrialDataViewModel,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit = {},
    onShareClick: (String) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedTrialForDelete by remember { mutableStateOf(TrialIdentifier.emptyTrialId) }
    val selectedTrialNumber: Int =
        uiState.selectedNBackTrialIds.size + uiState.selectedBartTrialIds.size
    if (showDeleteDialog) {
        ParticipantDialog(
            title = R.string.participants_data_delete_dialog_title,
            content = R.string.participants_data_delete_dialog_content,
            onConfirm = {
                viewModel.deleteTrial(selectedTrialForDelete)
                showDeleteDialog = false
            }
        ) { showDeleteDialog = false }
    }
    Scaffold(
        topBar = {
            DanCognitionTopAppBar(
                headerResId = R.string.participants_data_header,
                wantMenuButton = true,
                menuIcon = Icons.Default.Share,
                menuItems = listOf(
                    DanMenuItem(
                        "Download $selectedTrialNumber selected",
                        R.drawable.baseline_download_24,
                        "SELECTED"
                    ),
                    DanMenuItem("Download all", R.drawable.baseline_done_all_24, "ALL"),
                ),
                onMenuItemClick = {
                    onShareClick(it.key)
                },
                wantBackButton = true,
            ) {
                onBackPress()
            }
        },
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            ParticipantDataTopCard(
                participantName = uiState.selectedParticipant.name,
                participantId = uiState.selectedParticipant.userGivenId,
                participantNotes = uiState.selectedParticipant.notes,
                nBackTrialIds = uiState.selectedNBackTrialIds,
                bartTrialIds = uiState.selectedBartTrialIds
            )
            CollapsibleParticipantDataItemsGroups(
                title = "BART Data",
                items = mapToTrialDataFields(
                    name = uiState.selectedParticipant.name,
                    bartEntities = uiState.allBartTrials
                ),
                onDeleteClicked = { trial ->
                    selectedTrialForDelete = trial
                    showDeleteDialog = true
                },
                uiState = uiState,
                modifier = Modifier.padding(horizontal = 12.dp),
            ) { trial ->
                viewModel.toggleToSelectedTrialsList(trial)
            }
            CollapsibleParticipantDataItemsGroups(
                title = "NBACK Data",
                items = mapToTrialDataFields(
                    name = uiState.selectedParticipant.name,
                    nBackEntities = uiState.allNBackTrials
                ),
                onDeleteClicked = { trial ->
                    selectedTrialForDelete = trial
                    showDeleteDialog = true
                },
                uiState = uiState,
                modifier = Modifier.padding(horizontal = 12.dp),
            ) { trial ->
                viewModel.toggleToSelectedTrialsList(trial)
            }
        }

    }
}

@Composable
fun CollapsibleParticipantDataItemsGroups(
    title: String,
    items: List<TrialDataFields>,
    uiState: ParticipantsTrialDataUiState,
    modifier: Modifier = Modifier,
    onDeleteClicked: (TrialIdentifier) -> Unit = {},
    onCheckBoxClick: (TrialIdentifier) -> Unit = {},
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
                Text(
                    text = stringResource(id = R.string.participants_data_no_trials_message),
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            } else {
                items.forEach {
                    ParticipantDataItem(
                        participantDataFields = it,
                        onDeleteClicked = { onDeleteClicked(it) },
                        uiState = uiState
                    ) {
                        onCheckBoxClick(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ParticipantDataItem(
    participantDataFields: TrialDataFields,
    uiState: ParticipantsTrialDataUiState,
    modifier: Modifier = Modifier,
    onDeleteClicked: (TrialIdentifier) -> Unit = {},
    onCheckBoxClick: (TrialIdentifier) -> Unit = {},
) {
    val trialIdentifier: TrialIdentifier = participantDataFields.toTrialIdentifier()
    var isChecked by remember {
        mutableStateOf(
            when (trialIdentifier.testType) {
                TestType.BART -> {
                    trialIdentifier.trialId in uiState.selectedBartTrialIds
                }

                TestType.NBACK -> {
                    trialIdentifier.trialId in uiState.selectedNBackTrialIds
                }
            }
        )
    }
    ListItem(
        headlineContent = { Text(text = participantDataFields.trialTime.name) },
        overlineContent = {
            Text(
                text = "${participantDataFields.participantName}'s " +
                        "(id: ${participantDataFields.danParticipantId}) " +
                        "${participantDataFields.testType.name} data"
            )
        },
        supportingContent = { Text(text = participantDataFields.trialDay.name) },
        leadingContent = {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckBoxClick(participantDataFields.toTrialIdentifier())
                }
            )
        },
        trailingContent = {
            IconButton(onClick = { onDeleteClicked(participantDataFields.toTrialIdentifier()) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 4.dp)
            .border(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant),
                shape = RoundedCornerShape(10.dp)
            )
    )
}

@Composable
fun DataDropDownMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    menuItems: List<DanMenuItem>,
    onDismiss: () -> Unit = {},
    onClickMenuItem: (DanMenuItem) -> Unit = {}
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismiss() },
        modifier = modifier
    ) {
        var counter = menuItems.size
        menuItems.forEach {
            counter--
            DropdownMenuItem(
                leadingIcon = {
                    Icon(painter = painterResource(id = it.iconRes), null)
                },
                text = { Text(text = it.text) },
                onClick = {
                    onClickMenuItem(it)
                    onDismiss()
                }
            )
            if (counter != 0) {
                Divider()
            }
        }
    }
}

@Composable
fun ParticipantDataTopCard(
    participantName: String,
    participantId: String,
    participantNotes: String,
    bartTrialIds: List<Int>,
    nBackTrialIds: List<Int>,
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
                    id = R.string.participants_participant_name_label
                ) + ": "
                        + participantName
            )
            Text(
                text = stringResource(
                    id = R.string.participants_participant_id_label
                ) + ": "
                        + participantId
            )
            if (participantNotes != "") {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = participantNotes)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Selected Bart Trials: ${bartTrialIds.size}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Selected NBack Trials: ${nBackTrialIds.size}")
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

private fun TrialDataFields.toTrialIdentifier(): TrialIdentifier = TrialIdentifier(
    trialId = this.trialId,
    testType = this.testType,
)
