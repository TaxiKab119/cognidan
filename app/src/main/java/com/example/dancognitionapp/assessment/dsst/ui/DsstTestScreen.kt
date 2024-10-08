package com.example.dancognitionapp.assessment.dsst.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.dsst.ui.DsstIcons.DSST_ICONS
import com.example.dancognitionapp.utils.LandscapePreview
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.AssessmentInstructionsDialog


@Composable
fun DsstTestScreenRoot(
    viewModel: DsstScreenViewModel,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    DsstTestScreenContent(
        showIncorrectFeedback = uiState.showIncorrectFeedback,
        showCorrectFeedback = uiState.showCorrectFeedback,
        stimulus = uiState.stimulus,
        bottomIcons = uiState.bottomIcons,
        showDialog = uiState.showDialog,
        testStarted = uiState.testStarted,
        testEnded = uiState.testEnded,
        isPractice = uiState.isPractice,
        numCorrect = uiState.numberCorrect,
        numIncorrect = uiState.numberIncorrect,
        numClicks = uiState.numberClicks,
        modifier = modifier,
        onAction = {
            when (it) {
                DsstAction.CancelClickedDialog -> onCancelClick()
                else -> viewModel.onAction(it)
            }
        },
    )
}

@Composable
fun DsstTestScreenContent(
    showIncorrectFeedback: Boolean,
    showCorrectFeedback: Boolean,
    stimulus: Int,
    showDialog: Boolean,
    testStarted: Boolean,
    testEnded: Boolean,
    isPractice: Boolean,
    numCorrect: Int,
    numIncorrect: Int,
    numClicks: Int,
    @DrawableRes bottomIcons: List<Int>,
    onAction: (DsstAction) -> Unit,
    modifier: Modifier = Modifier
) {
    // A flag to trigger the 'Tally Counter' Animation even when stimulus are the same back to back
    var stimulusAnimationTrigger by remember { mutableStateOf(true) }

    Surface(modifier = modifier) {
        if (showDialog) {
            DsstDialog(
                isPractice = isPractice,
                onCancelClick = { onAction(DsstAction.CancelClickedDialog) },
                onOkClick = { onAction(DsstAction.OkClickedDialog) },
                testEnded = testEnded,
                numCorrect = numCorrect,
                numIncorrect = numIncorrect,
                numClicks = numClicks,
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp)
            ) {
                KeysRow()
                // Stimuli Box
                MiddleRowDSST(
                    stimulus,
                    stimulusAnimationTrigger,
                    Modifier,
                    showIncorrectFeedback,
                    showCorrectFeedback
                )
                // Buttons to Press
                KeysRow(
                    spacing = 12.dp,
                    isClickable = testStarted,
                    showNumbers = false,
                    icons = bottomIcons,
                    modifier = Modifier.padding(bottom = 18.dp)
                ) {
                    onAction(DsstAction.KeyPressed(it, stimulus))
                    stimulusAnimationTrigger = !stimulusAnimationTrigger
                }
            }
        }
    }
}

@Composable
fun MiddleRowDSST(
    stimulus: Int,
    stimulusAnimationTrigger: Boolean,
    modifier: Modifier = Modifier,
    showIncorrectFeedback: Boolean = false,
    showCorrectFeedback: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            FeedBackSquare(
                feedbackText = "Correct",
                color = Color(0xff89f08e),
                showFeedback = showCorrectFeedback
            )
            FeedBackSquare(
                feedbackText = "Incorrect",
                color = MaterialTheme.colorScheme.errorContainer,
                showFeedback = showIncorrectFeedback
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, Color.Blue, RoundedCornerShape(2.dp))
        ) {
            StimulusText(stimulus, stimulusAnimationTrigger)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StimulusText(
    stimulus: Int,
    stimulusAnimationTrigger: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = Pair(stimulus, stimulusAnimationTrigger),
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut()
        },
        label = "Tally Counter",
    ) {
        Text(
            text = "$stimulus",
            fontSize = 24.sp,
            modifier = modifier
        )
    }
}

@Composable
fun FeedBackSquare(
    feedbackText: String,
    color: Color,
    showFeedback: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = showFeedback,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            Text(
                text = feedbackText,
                fontSize = 18.sp,
                modifier = Modifier
                    .background(color)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun KeysRow(
    modifier: Modifier = Modifier,
    isClickable: Boolean = false,
    showNumbers: Boolean = true,
    @DrawableRes icons: List<Int> = DSST_ICONS,
    spacing: Dp = 1.dp,
    onButtonClick: (Int) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing, Alignment.CenterHorizontally),
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (i in icons.indices) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .size(60.dp)
                        .border(1.dp, Color.Black)
                ) {
                    val randomIcon = icons[i]
                    IconButton(
                        enabled = isClickable,
                        onClick = { onButtonClick(randomIcon) },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = randomIcon),
                            contentDescription = randomIcon.toString(),
                            tint = Color.Black
                        )
                    }
                }
                if (showNumbers) {
                    Text(
                        text = "${i + 1}",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DsstDialog(
    isPractice: Boolean,
    testEnded: Boolean,
    numCorrect: Int,
    numIncorrect: Int,
    numClicks: Int,
    onCancelClick: () -> Unit = {},
    onOkClick: () -> Unit = {}
) {
    if (testEnded) {
        AlertDialog(
            onDismissRequest = { onCancelClick() },
            confirmButton = {
                Button(onClick = { onCancelClick() }) {
                    Text(text = "Click to leave")
                }
            },
            title = {
                Text(text = "DSST Completed")
            },
            text = {
                Column {
                    if (isPractice) {
                        Text(text = "Number of Correct Clicks: $numCorrect")
                        Text(text = "Number of Incorrect Clicks: $numIncorrect")
                        // Avoids divide by zero failures
                        val percentCorrect: Float = if (numClicks != 0) {
                            (numCorrect.toFloat() / numClicks) * 100
                        } else {
                            0f
                        }
                        Text(text = "Percentage of Correct Clicks: ${"%.2f".format(percentCorrect)}%")
                    }
                }
            },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    } else {
        AssessmentInstructionsDialog(
            assessmentTitle = R.string.dsst_name_expanded,
            onCancelClick = onCancelClick,
            onOkClick = onOkClick,
            showCancel = isPractice
        ) {
            Text(
                text = stringResource(id = R.string.dsst_test_instructions1),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.dsst_test_instructions2),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.dsst_test_instructions3),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )
        }
    }
}


@LandscapePreview
@Composable
private fun DsstTestScreenPreview() {
    DanCognitionAppTheme {
        DsstTestScreenContent(
            showIncorrectFeedback = false,
            showCorrectFeedback = true,
            stimulus = 3,
            bottomIcons = DSST_ICONS.shuffled(),
            onAction = {},
            testEnded = false,
            testStarted = false,
            isPractice = true,
            showDialog = false,
            numClicks = 0,
            numIncorrect = 0,
            numCorrect = 0
        )
    }
}


@LandscapePreview
@Composable
private fun DsstTestDialogPreview() {
    DanCognitionAppTheme {
        DsstTestScreenContent(
            showIncorrectFeedback = false,
            showCorrectFeedback = true,
            stimulus = 3,
            bottomIcons = DSST_ICONS.shuffled(),
            onAction = {},
            testEnded = true,
            testStarted = false,
            isPractice = true,
            showDialog = true,
            numClicks = 5,
            numIncorrect = 3,
            numCorrect = 2
        )
    }
}
