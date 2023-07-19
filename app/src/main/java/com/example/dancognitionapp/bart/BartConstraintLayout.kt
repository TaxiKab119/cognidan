package com.example.dancognitionapp.bart

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LockScreenOrientation
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import timber.log.Timber


@Composable
fun BartConstraintLayout(modifier: Modifier = Modifier) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    // Replace with ViewModel later
    var balloonReward: Int by remember { mutableStateOf(1) }
    val totalEarnings: Int = 0

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (
            inflateButton,
            collectButton,
            leftHeader,
            rightHeader,
            balloon,
            dollarsLeft,
            dollarsRight
        ) = createRefs()

        Text(
            text = stringResource(id = R.string.bart_reward_for_balloon),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.constrainAs(leftHeader) {
                start.linkTo(parent.start, margin = 12.dp)
                top.linkTo(parent.top, margin = 12.dp)
            }
        )
        Text(
            text = "$$balloonReward.00",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.constrainAs(dollarsLeft) {
                centerHorizontallyTo(leftHeader)
                top.linkTo(leftHeader.bottom)
            }
        )
        Text(
            text = stringResource(id = R.string.bart_total_earnings),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.constrainAs(rightHeader) {
                end.linkTo(parent.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 12.dp)
            }
        )
        Text(
            text = "$$totalEarnings.00",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.constrainAs(dollarsRight) {
                centerHorizontallyTo(rightHeader)
                top.linkTo(rightHeader.bottom)
            }
        )

        // Remove this -> we want to use Guidelines...
        createHorizontalChain(inflateButton, balloon, collectButton)

        BartButton(
            contentsId = R.string.bart_inflate_button_label,
            onClick = {
                balloonReward++
                Timber.i("$balloonReward") },
            modifier = Modifier.constrainAs(inflateButton) {
                bottom.linkTo(parent.bottom, margin = 12.dp)
                start.linkTo(parent.start, margin = 12.dp)
                width = Dimension.fillToConstraints
            }
        )
        Box(modifier = Modifier.size(260.dp))
        BartButton(
            contentsId = R.string.bart_collect_button_label,
            onClick = { balloonReward = 0 },
            modifier = Modifier.constrainAs(collectButton) {
                bottom.linkTo(parent.bottom, margin = 12.dp)
                end.linkTo(parent.end, margin = 12.dp)
                width = Dimension.fillToConstraints

            }
        )



    }
}

/**
 * Composable for Text fields in the BART screen
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
            style = MaterialTheme.typography.displaySmall
        )
    }
    if (dollarValue != null) {
        Text(
            text = "$$dollarValue.00",
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=393dp,height=851dp,orientation=landscape"
)
@Composable
fun BartConstraintLayoutPreview() {
    DanCognitionAppTheme {
        BartConstraintLayout(modifier = Modifier.fillMaxSize())
    }
}