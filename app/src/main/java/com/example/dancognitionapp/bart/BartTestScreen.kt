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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import timber.log.Timber


@Composable
fun BartTestScreen(modifier: Modifier = Modifier) {

    // Replace with ViewModel later
    var balloonReward: Int by remember { mutableStateOf(1) }
    var totalEarnings: Int by remember { mutableStateOf(0) }

    BoxWithConstraints(modifier = modifier
        .padding(12.dp)
    ) {
        val initialBalloonRadius = (maxWidth.value / 9)
        var balloonRadius: Float by remember { mutableStateOf(initialBalloonRadius) }

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

            /**
             * Setting the guidelines to 0 just shows their uselessness...
             * I think because of the horizontal chain
             * can easily get rid of them all.
             * */
            val leftGuideline = createGuidelineFromStart(0f)
            val rightGuideline = createGuidelineFromEnd(0f)

            BartTitleText(
                textResId = R.string.bart_reward_for_balloon,
                modifier = Modifier.constrainAs(leftHeader) {
                    start.linkTo(parent.start)
                    end.linkTo(leftGuideline)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    centerHorizontallyTo(inflateButton)
                }
            )
            BartTitleText(
                dollarValue = balloonReward,
                modifier = Modifier.constrainAs(dollarsLeft) {
                    centerHorizontallyTo(leftHeader)
                    top.linkTo(leftHeader.bottom)
                }
            )
            BartTitleText(
                textResId = R.string.bart_total_earnings,
                modifier = Modifier.constrainAs(rightHeader) {
                    start.linkTo(rightGuideline)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    centerHorizontallyTo(collectButton)
                }
            )
            BartTitleText(
                dollarValue = totalEarnings,
                modifier = Modifier.constrainAs(dollarsRight) {
                    centerHorizontallyTo(rightHeader)
                    top.linkTo(rightHeader.bottom)
                }
            )

            createHorizontalChain(inflateButton, balloon, collectButton)
            BartButton(
                contentsId = R.string.bart_inflate_button_label,
                onClick = {
                    balloonReward++
                    balloonRadius *= 1.08f
                    Timber.i("$balloonReward")
                },
                modifier = Modifier.constrainAs(inflateButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(leftGuideline)
                    width = Dimension.fillToConstraints
                }
            )
            Balloon(
                modifier = modifier
                    .constrainAs(balloon) {
                        start.linkTo(leftGuideline)
                        end.linkTo(rightGuideline)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                radius = balloonRadius
            )
            BartButton(
                contentsId = R.string.bart_collect_button_label,
                onClick = {
                    totalEarnings += balloonReward
                    balloonReward = 1
                    balloonRadius = initialBalloonRadius
                },
                modifier = Modifier.constrainAs(collectButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(rightGuideline)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints

                }
            )
        }
    }
}

/**
 * Composable for Text in the BART screen
 * */
@Composable
fun BartTitleText(
    textResId: Int? = null,
    dollarValue: Int? = null,
    modifier: Modifier = Modifier
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick() } ,
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
        BartTestScreen(modifier = Modifier.fillMaxSize())
    }
}