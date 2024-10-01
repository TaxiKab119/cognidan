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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dancognitionapp.assessment.dsst.ui.DsstIcons.ICONS
import com.example.dancognitionapp.utils.LandscapePreview
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme


@Composable
fun DsstTestScreenRoot(
    viewModel: DsstScreenViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    DsstTestScreenContent(
        showIncorrectFeedback = uiState.showIncorrectFeedback,
        showCorrectFeedback = uiState.showCorrectFeedback,
        stimulus = uiState.stimulus,
        onAction = viewModel::onAction,
        bottomIcons = uiState.bottomIcons,
        modifier = modifier
    )
}

@Composable
fun DsstTestScreenContent(
    showIncorrectFeedback: Boolean,
    showCorrectFeedback: Boolean,
    stimulus: Int,
    @DrawableRes bottomIcons: List<Int>,
    onAction: (DsstAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 18.dp)
        ) {
            KeysRow()
            // Stimuli Box
            MiddleRowDSST(stimulus, Modifier, showIncorrectFeedback, showCorrectFeedback)
            // Buttons to Press
            KeysRow(
                spacing = 12.dp,
                isClickable = true,
                showNumbers = false,
                icons = bottomIcons,
                modifier = Modifier.padding(bottom = 18.dp)
            ) {
                onAction(DsstAction.KeyPressed(it))
            }
        }
    }
}

@Composable
fun MiddleRowDSST(
    stimulus: Int,
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
            StimulusText(stimulus = stimulus)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun StimulusText(
    stimulus: Int,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = stimulus,
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut()
        }
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
    @DrawableRes icons: List<Int> = ICONS,
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

@LandscapePreview
@Composable
private fun DsstTestScreenPreview() {
    DanCognitionAppTheme {
        DsstTestScreenContent(
            showIncorrectFeedback = false,
            showCorrectFeedback = true,
            stimulus = 3,
            bottomIcons = ICONS.shuffled(),
            {}
        )
    }
}