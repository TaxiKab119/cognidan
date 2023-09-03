package com.example.dancognitionapp.assessment.bart.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.bart.ui.components.BalloonCanvas
import com.example.dancognitionapp.assessment.bart.ui.components.BartDialog


@Composable
fun BartTestScreen(
    viewModel: BartViewModel,
    uiState: BartUiState,
    modifier: Modifier = Modifier,
    navigateTo: (Int) -> Unit
) {
    BoxWithConstraints(modifier = modifier
        .padding(12.dp)
    ) {
        val initialBalloonRadius = (maxWidth.value / 9)
        var balloonRadius: Float by remember { mutableStateOf(initialBalloonRadius) }
        var showDialog by remember { mutableStateOf(false) }

        ConstraintLayout(modifier = modifier) {

            val (
                inflateButton,
                collectButton,
                leftHeader,
                rightHeader,
                balloon,
                dollarsLeft,
                dollarsRight
            ) = createRefs()
            if (uiState.isBalloonPopped || uiState.isTestComplete) {
                showDialog = true
                BartDialog(
                    isTestComplete = uiState.isTestComplete,
                    onDismiss = {
                        if (uiState.isTestComplete) {
                            navigateTo(R.id.selection_dest)
                        } else {
                            viewModel.hideDialog()
                            showDialog = false
                            balloonRadius = initialBalloonRadius
                            viewModel.resetBalloonStatus()
                        }
                    }
                )
            }

            BalloonCanvas(
                modifier = modifier
                    .constrainAs(balloon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                radius = if (uiState.isBalloonPopped) initialBalloonRadius
                    else balloonRadius
            )
            createHorizontalChain(inflateButton, balloon, collectButton)

            BartTitleText(
                textResId = R.string.bart_reward_for_balloon,
                modifier = Modifier.constrainAs(leftHeader) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    centerHorizontallyTo(inflateButton)
                }
            )
            BartTitleText(
                dollarValue = uiState.currentReward,
                modifier = Modifier.constrainAs(dollarsLeft) {
                    centerHorizontallyTo(leftHeader)
                    top.linkTo(leftHeader.bottom)
                }
            )
            BartTitleText(
                textResId = R.string.bart_total_earnings,
                modifier = Modifier.constrainAs(rightHeader) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    centerHorizontallyTo(collectButton)
                }
            )
            BartTitleText(
                dollarValue = uiState.totalEarnings,
                modifier = Modifier.constrainAs(dollarsRight) {
                    centerHorizontallyTo(rightHeader)
                    top.linkTo(rightHeader.bottom)
                }
            )
            BartButton(
                contentsId = R.string.bart_inflate_button_label,
                modifier = Modifier.constrainAs(inflateButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
            ) {
                if (!showDialog) {
                    viewModel.inflateBalloon()
                    balloonRadius *= 1.08f
                }
            }
            BartButton(
                contentsId = R.string.bart_collect_button_label,
                modifier = Modifier.constrainAs(collectButton) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                if (!showDialog) {
                    viewModel.collectBalloonReward()
                    balloonRadius = initialBalloonRadius
                }
            }
        }
    }
}

/**
 * Composable for Text in the BART screen
 * */
@Composable
fun BartTitleText(
    modifier: Modifier = Modifier,
    textResId: Int? = null,
    dollarValue: Int? = null
) {
    if (textResId != null) {
        Text(
            text = stringResource(id = textResId),
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier,
            textAlign = TextAlign.Center
        )
    }
    if (dollarValue != null) {
        Text(
            text = "$$dollarValue.00",
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier
        )
    }
}
@Composable
fun BartButton(
    @StringRes contentsId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(12.dp)
    ) {
        Text(
            text = stringResource(id = contentsId),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

//@LandscapePreview
//@Composable
//fun BartConstraintLayoutPreview() {
//    DanCognitionAppTheme {
//        BartTestScreen(modifier = Modifier.fillMaxSize(), {})
//    }
//}