package com.example.dancognitionapp.assessment.selection

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.DanCognitionTopAppBar
import timber.log.Timber


@Composable
fun SelectTestDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: TrialDetailsViewModel,
    uiState: TrialDetailsUiState,
    participantList: List<Participant>,
    onBackPress: () -> Unit,
    onSelectTrialTime: (TrialTime) -> Unit = {},
    onSelectTrialDay: (TrialDay) -> Unit = {},
    onSelectParticipant: (Participant) -> Unit = {},
    onConfirmClick: () -> Unit,
) {
    Timber.i("$uiState")
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Image(
                    painterResource(id = R.drawable.cognidan_final_noblank),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { Text(text = "Please fill in all fields")},
            text = { Text(text = stringResource(id = R.string.selection_dialog_fields_empty_message))},
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = R.string.ok_button))
                }
            },
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                DanCognitionTopAppBar(
                    headerResId = R.string.selection_trial_details_header,
                    wantBackButton = true
                ) {
                    onBackPress()
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (viewModel.areTrialDetailsFilled()) {
                            onConfirmClick()
                        } else {
                            showDialog = true
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                ) {
                    Text(text = "Confirm")
                }
            }
        ) {
            SelectTrialDetailsContent(
                modifier = modifier.padding(it),
                participantList = participantList,
                trialTimes = arrayOf(TrialTime.PRE_DIVE, TrialTime.DIVE, TrialTime.POST_DIVE),
                trialDays = arrayOf(TrialDay.DAY_1, TrialDay.DAY_2),
                uiState = uiState,
                onSelectTrialDay = { trialDay -> onSelectTrialDay(trialDay) },
                onSelectTrialTime = { trialTime -> onSelectTrialTime(trialTime) }
            ) { participant ->
                onSelectParticipant(participant)
            }
        }
    }
}
@Composable
fun SelectTrialDetailsContent(
    modifier: Modifier = Modifier,
    participantList: List<Participant>,
    trialTimes: Array<TrialTime>,
    trialDays: Array<TrialDay>,
    uiState: TrialDetailsUiState,
    onSelectTrialTime: (TrialTime) -> Unit = {},
    onSelectTrialDay: (TrialDay) -> Unit = {},
    onSelectParticipant: (Participant) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectionDropDownMenu(
            titleRes = R.string.selection_select_participant,
            menuOptions = participantList.map { it.name },
            labelRes = R.string.participants_participant_name_label,
            selectedItem = uiState.selectedParticipant?.name ?: ""
        ) { participantName ->
            val selectedParticipant = participantList.find { it.name ==  participantName }
            Timber.i("Selected Participant = $selectedParticipant")
            onSelectParticipant(selectedParticipant ?: Participant(id = 0, "", "", ""))
        }
        SelectionDropDownMenu(
            titleRes = R.string.selection_select_trial_day,
            menuOptions = trialDays.map { it.name },
            labelRes = R.string.selection_trial_day_label,
            selectedItem = uiState.selectedTrialDay?.name ?: ""
        ) { trialDayString ->
            val selectedTime = trialDays.find { it.name == trialDayString }
            Timber.i("Selected Trial Day = $selectedTime")
            onSelectTrialDay(selectedTime ?: TrialDay.DAY_1)
        }
        SelectionDropDownMenu(
            titleRes = R.string.selection_select_trial_time,
            menuOptions = trialTimes.map { it.name },
            labelRes = R.string.selection_trial_time_label,
            selectedItem = uiState.selectedTrialTime?.name ?: ""
        ) { trialTimeString ->
            val selectedTime = trialTimes.find { it.name == trialTimeString }
            Timber.i("Selected Trial Time = $selectedTime")
            onSelectTrialTime(selectedTime ?: TrialTime.PRE_DIVE)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionDropDownMenu(
    @StringRes titleRes: Int,
    @StringRes labelRes: Int,
    menuOptions: List<String>,
    selectedItem: String = "",
    onSelectItem: (String) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(Modifier.padding(12.dp)) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedItem,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = labelRes)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    for (item in menuOptions) {
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                onSelectItem(item)
                                isExpanded = false
                            }
                        )
                    }
                }
            }

        }
    }
}

private val testParticipants = listOf<Participant>(
    Participant(
        id = 1,
        name = "Alex Balan",
        notes = "note",
        userGivenId = "001"
    ),
    Participant(
        id = 2,
        name = "Ana Rumpl",
        notes = "note",
        userGivenId = "002"
    ),
    Participant(
        id = 3,
        name = "SpongeBob SquarePants",
        notes = "note",
        userGivenId = "003"
    ),
    Participant(
        id = 4,
        name = "Sandy Cheeks",
        notes = "note",
        userGivenId = "004"
    ),
    Participant(
        id = 1,
        name = "Alex Balan",
        notes = "note",
        userGivenId = "001"
    ),
    Participant(
        id = 2,
        name = "Ana Rumpl",
        notes = "note",
        userGivenId = "002"
    ),
    Participant(
        id = 3,
        name = "SpongeBob SquarePants",
        notes = "note",
        userGivenId = "003"
    ),
    Participant(
        id = 4,
        name = "Sandy Cheeks",
        notes = "note",
        userGivenId = "004"
    ),
    Participant(
        id = 1,
        name = "Alex Balan",
        notes = "note",
        userGivenId = "001"
    ),
    Participant(
        id = 2,
        name = "Ana Rumpl",
        notes = "note",
        userGivenId = "002"
    ),
    Participant(
        id = 3,
        name = "SpongeBob SquarePants",
        notes = "note",
        userGivenId = "003"
    ),
    Participant(
        id = 4,
        name = "Sandy Cheeks",
        notes = "note",
        userGivenId = "004"
    )
)


@Preview(showBackground = true)
@Composable
fun SelectionDropDownMenuPreview() {
    DanCognitionAppTheme {
        SelectionDropDownMenu(
            titleRes = R.string.selection_select_participant,
            menuOptions = testParticipants.map { it.name },
            labelRes = R.string.participants_participant_id_label
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SelectTestDetailsContentPreview() {
    DanCognitionAppTheme {
        SelectTrialDetailsContent(
            participantList = testParticipants,
            trialTimes = TrialTime.values(),
            trialDays = TrialDay.values(),
            uiState = TrialDetailsUiState()
        )
    }
}


