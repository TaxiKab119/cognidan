package com.example.dancognitionapp.participants


import android.view.MotionEvent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.participants.data.ParticipantRepository.participantList
import com.example.dancognitionapp.participants.data.ParticipantUiState
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantManagerScreen(
    uiState: ParticipantUiState,
    goToAddScreen: () -> Unit = {},
    goToEditScreen: (Int) -> Unit = {}
) {
    var selectedParticipantId: Int by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    var isBottomSheetExpanded by remember { mutableStateOf(false) }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = false,
            confirmValueChange =  {
                when(it) {
                    SheetValue.Expanded -> {
                        isBottomSheetExpanded = true
                        true
                    }
                    SheetValue.Hidden -> {
                        isBottomSheetExpanded = false
                        true
                    }
                    else -> true
                }
            }
        ),
    )

    LaunchedEffect(key1 = Unit) {
        scaffoldState.bottomSheetState.hide()
    }
    BottomSheetScaffold(
        sheetContent = {
            ParticipantsBottomSheetContent(
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .padding(12.dp)
            ) {
                goToEditScreen(selectedParticipantId)
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
    ) {
        val listState = rememberLazyListState()
        val fabExtended by remember { derivedStateOf { !listState.isScrollInProgress } }
        val hideBottomSheet by remember { derivedStateOf { listState.isScrollInProgress }}
        val haptic = LocalHapticFeedback.current

        Scaffold(
            topBar = {
                DanCognitionTopAppBar(headerResId = R.string.landing_participant_manager)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    expanded = fabExtended,
                    onClick = {
                        goToAddScreen()
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
                if (hideBottomSheet) {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                        isBottomSheetExpanded = false
                    }
                }
                items(uiState.participantList) {participant ->
                    ParticipantCard(
                        participant = participant,
                        modifier = Modifier
                            .padding(8.dp),
                        onClick = {
                            if (isBottomSheetExpanded) {
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                    isBottomSheetExpanded = false
                                }
                            }

                        }
                    ) {
                        selectedParticipantId = participant.internalId
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        if (!isBottomSheetExpanded) {
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                                isBottomSheetExpanded = true
                            }
                        }
                    }
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
    onClick: () -> Unit = {},
    onLongClick: (Participant) -> Unit = {}
) {
    Card(
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
            .combinedClickable(
                onClick = { onClick() },
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

@ExperimentalMaterial3Api
@Composable
fun ParticipantsBottomSheetContent(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    Column(modifier) {
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .weight(1f),
            enabled = false
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Export to .csv")
            Spacer(Modifier.width(12.dp))
            Text(text = "Export to .csv")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = { onEditClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Export to .csv")
            Spacer(Modifier.width(12.dp))
            Text(text = "Edit Participant")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ParticipantManagerScreenPreview() {
    DanCognitionAppTheme {
        val viewModel: ParticipantsViewModel = viewModel()
        val uiState: ParticipantUiState by viewModel.uiState
        ParticipantManagerScreen(
            uiState = uiState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantCardPreview() {
    DanCognitionAppTheme {
        ParticipantCard(participantList.first())
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ParticipantsBottomSheetPreview() {
    DanCognitionAppTheme {
        ParticipantsBottomSheetContent(modifier = Modifier.fillMaxWidth(), {})
    }
}