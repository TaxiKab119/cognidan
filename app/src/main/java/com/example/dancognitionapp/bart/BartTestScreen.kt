package com.example.dancognitionapp.bart

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme


@Composable
fun BartTestScreen(modifier: Modifier = Modifier, onTestFinished: (Int) -> Unit) {

    val viewModel: BartViewModel = viewModel()
    val uiState by viewModel.uiState

    BoxWithConstraints(modifier = modifier
        .padding(12.dp)
    ) {
        val initialBalloonRadius = (maxWidth.value / 9)
        var balloonRadius: Float by rememberSaveable { mutableStateOf(initialBalloonRadius) }

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

            BartDialog(
                showDialog = uiState.showDialog,
                isTestComplete = uiState.balloonList.size == 0,
                onDismiss = { viewModel.hideDialog() }
            ) { dest ->
                onTestFinished(dest)
            }

            BalloonCanvas(
                modifier = modifier
                    .constrainAs(balloon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                radius = if (uiState.balloonPopped) initialBalloonRadius
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
                /**
                 * This `if` block is needed to reset [balloonRadius] when starting a new balloon
                 *  as balloonRadius is set based on the size of this composable initially it
                 *  can't be updated outside of the UI layer.
                 *  Think of balloonPopped as balloonJustPopped
                */
                if (uiState.balloonPopped) {
                    balloonRadius = initialBalloonRadius
                    viewModel.resetBalloonStatus()
                    viewModel.inflateBalloon()
                    balloonRadius *= 1.08f
                } else {
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
                viewModel.collectBalloonReward()
                balloonRadius = initialBalloonRadius
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

@LandscapePreview
@Composable
fun BartConstraintLayoutPreview() {
    DanCognitionAppTheme {
        BartTestScreen(modifier = Modifier.fillMaxSize(), {})
    }
}