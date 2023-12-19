package com.example.dancognitionapp.assessment.nback.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.widget.ResponsiveText

enum class NBackFeedbackState {
    HIT,
    FALSE_ALARM,
    INTERMEDIATE
}

// Only commented this out because it's still awesome and don't want to get rid of it
//val slyRemarks = listOf(
//    "Wow that was bad!",
//    "Trigger happy much?",
//    "I\'d be better with my eyes closed",
//    "Incorrect...Loser",
//    "Consult an optometrist",
//    "Why did you click that one?"
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NBackScreen(
    isPractice: Boolean,
    viewModel: NBackViewModel,
    uiState: NBackUiState,
    goToBart: () -> Unit = {},
    returnToSelect: () -> Unit = {}
) {
    val stimuli = listOf('A', 'B', 'C', 'D', 'Z', 'E', 'F', 'G', 'H')
    val interactionSource = remember { MutableInteractionSource() }
    if (uiState.isEndOfTest) {
        AlertDialog(
            onDismissRequest = { /*Do Nothing*/ },
            title = { Text(text = "Test Complete") },
            text = {
                if (isPractice) {
                    Text(text = "Click OK to leave")
                } else {
                    Text(text = "Continue to BART")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (isPractice) {
                            returnToSelect()
                        } else {
                            goToBart()
                        }
                    },
                    modifier = Modifier.padding(8.dp),
                    border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text(
                        text = stringResource(id = R.string.ok_button),
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        )
    }
    if (uiState.isFalseAlarm) {
        Dialog(
            onDismissRequest = { /*Do Nothing*/ }
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)

            ) {
                Text(
                    text = "You missed! Incorrect.",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (uiState.isTestScreenClickable && !uiState.hasUserClicked) {
                    val clickTime = System.currentTimeMillis()
                    viewModel.participantClick(uiState.currentItem, clickTime)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (uiState.showDialog) {
            NBackCustomDialog(
                isPractice = isPractice,
                nValue = uiState.nValue.value,
                onCancelClick = { returnToSelect() }
            ) {
                viewModel.startAdvancing()
            }
        }
        if (!uiState.showDialog) {
            NBackFeedback(
                feedbackState = uiState.feedbackState,
                modifier = Modifier.align(Alignment.TopCenter),
                isPractice = isPractice
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentSize(Alignment.Center)
                .border(4.dp, color = MaterialTheme.colorScheme.background)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (!uiState.showDialog) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    userScrollEnabled = false
                ) {
                    items(stimuli.size) { index ->
                        val stimulus = stimuli[index]
                        NBackQuadrant(
                            currentChar = uiState.currentItem.letter,
                            quadrantChar = stimulus
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NBackQuadrant(modifier: Modifier = Modifier, currentChar: Char = 'a', quadrantChar: Char) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(4.dp, Color.Black)
            .size(85.dp)
    ) {
        if (currentChar == quadrantChar) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Blue)
                    .align(Alignment.Center)
            )
        }
        if (quadrantChar == 'Z') {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Focus",
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}

@Composable
fun NBackFeedback(
    feedbackState: NBackFeedbackState,
    isPractice: Boolean,
    modifier: Modifier = Modifier,
) {
    val isPracticeResponse: Pair<String, Color> = "Clicked" to Color.Black
    val goodClickResponse: Pair<String, Color> = if (isPractice) ("Correct" to Color.Green) else isPracticeResponse
    val badClickResponse: Pair<String, Color> = if (isPractice) "Incorrect!" to Color.Red else isPracticeResponse

    val (text, color) = when(feedbackState) {
        NBackFeedbackState.HIT -> goodClickResponse
        NBackFeedbackState.FALSE_ALARM -> badClickResponse
        NBackFeedbackState.INTERMEDIATE -> "" to Color.Black
    }
    Text(
        text = text,
        modifier = modifier
            .offset(0.dp, 10.dp),
        style = MaterialTheme.typography.titleLarge,
        color = color
    )
}

@Composable
fun NBackCustomDialog(
    isPractice: Boolean,
    nValue: Int,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit = {},
    onOkClick: () -> Unit = {}
) {
    val context = LocalContext.current
    // Create a transparent background layer that covers the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .fillMaxWidth(0.75f)
                .padding(16.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = stringResource(id = R.string.nback_instructions_dialog_title, nValue),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                ResponsiveText(
                    text = context.resources.getQuantityString(R.plurals.nback_test_instructions, nValue, nValue),
                    maxLines = 5,
                    targetTextSize = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (isPractice) {
                    Spacer(modifier = Modifier.height(12.dp))
                    ResponsiveText(
                        text = stringResource(R.string.nback_feedback_instructions),
                        maxLines = 3,
                        targetTextSize = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    if (isPractice) {
                        TextButton(
                            onClick = { onCancelClick() },
                            modifier = Modifier.padding(8.dp),
                            border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel_button),
                                modifier = Modifier.padding(6.dp),
                            )
                        }
                    }
                    TextButton(
                        onClick = { onOkClick() },
                        modifier = Modifier.padding(8.dp),
                        border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok_button),
                            modifier = Modifier.padding(6.dp),
                        )
                    }
                }
            }
        }
    }
}