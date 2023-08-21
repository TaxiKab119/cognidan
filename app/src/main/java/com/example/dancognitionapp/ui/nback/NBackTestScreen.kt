package com.example.dancognitionapp.ui.nback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.nback.NBackKey
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

enum class NBackFeedbackState {
    HIT,
    //    MISS,
    FALSE_ALARM,
    //    CORRECT_REJECTION,
    INTERMEDIATE
}
val slyRemarks = listOf<String>(
    "Wow that was bad!",
    "Trigger happy much?",
    "I\'d be better with my eyes closed",
    "Incorrect...Loser",
    "Consult an optometrist",
    "Why did you click that one?"
)

@Composable
fun NBackScreen() {

    val viewModel: NBackViewModel = viewModel()
    val uiState by viewModel.uiState

    val stimuli = listOf('A', 'B', 'C', 'D', 'Z', 'E', 'F', 'G', 'H')
    val interactionSource = remember { MutableInteractionSource() }
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /*DO NOTHING*/ },
            title = { Text(text = "Click OK to start the Test") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        viewModel.startAdvancing()
                    }
                ) {
                    Text(text = "OK")
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
                viewModel.participantClick(uiState.currentItem)
            },
        contentAlignment = Alignment.Center
    ) {
        NBackFeedback(
            feedbackState = uiState.feedbackState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentSize(Alignment.Center)
                .border(8.dp, color = MaterialTheme.colorScheme.surface)
        ) {
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
                    .size(20.dp)
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

@LandscapePreview
@Composable
fun NBackScreenPreview() {
    DanCognitionAppTheme {
        NBackScreen()
    }
}