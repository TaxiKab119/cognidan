package com.example.dancognitionapp.ui.nback

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R

enum class NBackFeedbackState {
    HIT,
    //    MISS,
    FALSE_ALARM,
    //    CORRECT_REJECTION,
    INTERMEDIATE
}
val slyRemarks = listOf(
    "Wow that was bad!",
    "Trigger happy much?",
    "I\'d be better with my eyes closed",
    "Incorrect...Loser",
    "Consult an optometrist",
    "Why did you click that one?"
)

@Composable
fun NBackScreen(
    isPractice: Boolean,
    viewModel: NBackViewModel,
    uiState: NBackUiState,
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
                }
            },
            confirmButton = {
                if (isPractice) {
                    TextButton(onClick = { returnToSelect() }) {
                        Text(text = stringResource(id = R.string.ok_button))
                    }
                }
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (uiState.isTestScreenClickable && !uiState.hasUserClicked) {
                    viewModel.participantClick(uiState.currentItem)
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
        if (isPractice && !uiState.showDialog) {
            NBackFeedback(
                feedbackState = uiState.feedbackState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentSize(Alignment.Center)
                .border(8.dp, color = MaterialTheme.colorScheme.surface)
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
            .border(8.dp, Color.Black)
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
    }
}

@Composable
fun NBackFeedback(feedbackState: NBackFeedbackState, modifier: Modifier = Modifier) {
    val (text, color) = when(feedbackState) {
        NBackFeedbackState.HIT -> "Correct!" to Color.Green
        NBackFeedbackState.FALSE_ALARM -> slyRemarks.shuffled().first() to Color.Red
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
private fun ResponsiveText(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    text: String,
    targetTextSize: TextStyle
) {
    val typographySizes = listOf<TextStyle>(
        MaterialTheme.typography.displayLarge,
        MaterialTheme.typography.displayMedium,
        MaterialTheme.typography.displaySmall,
        MaterialTheme.typography.headlineLarge,
        MaterialTheme.typography.headlineMedium,
        MaterialTheme.typography.headlineSmall,
        MaterialTheme.typography.titleLarge,
        MaterialTheme.typography.titleMedium,
        MaterialTheme.typography.titleSmall,
        MaterialTheme.typography.bodyLarge,
        MaterialTheme.typography.bodyMedium,
        MaterialTheme.typography.bodySmall,
        MaterialTheme.typography.labelLarge,
        MaterialTheme.typography.labelMedium,
        MaterialTheme.typography.labelSmall,
    )

    var typographySizeIndex: Int by remember {
        mutableStateOf(typographySizes.indexOfFirst {it == targetTextSize})
    }
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            val currentMaxLineIndex = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(currentMaxLineIndex)) {
                typographySizeIndex--
            }
        },
        style = try {
            typographySizes[typographySizeIndex]
        } catch (e: IndexOutOfBoundsException) {
            MaterialTheme.typography.labelSmall
        }
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
                    targetTextSize = MaterialTheme.typography.titleSmall
                )
                if (isPractice) {
                    Spacer(modifier = Modifier.height(12.dp))
                    ResponsiveText(
                        text = stringResource(R.string.nback_feedback_instructions),
                        maxLines = 3,
                        targetTextSize = MaterialTheme.typography.titleSmall,
                    )
                }
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    if (isPractice) {
                        TextButton(onClick = { onCancelClick() }) {
                            Text(text = stringResource(id = R.string.cancel_button))
                        }
                    }
                    TextButton(
                        onClick = { onOkClick() }
                    ) {
                        Text(text = stringResource(id = R.string.ok_button))
                    }
                }
            }
        }
    }
}

//@LandscapePreview
//@Composable
//fun NBackScreenPreview() {
//    DanCognitionAppTheme {
//        NBackCustomDialog(isPractice = true)
//    }
//}