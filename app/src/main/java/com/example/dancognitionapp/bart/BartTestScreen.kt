package com.example.dancognitionapp.bart

import android.content.pm.ActivityInfo
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LockScreenOrientation
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

@Composable
fun BartScreen(
    modifier: Modifier = Modifier
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    val balloonDimens = getMaxOvalSizeAndIncrements(260, 20)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 0.dp)
                .fillMaxWidth()
        ) {
            BartText(R.string.bart_reward_for_balloon, 0)
            BartText(R.string.bart_total_earnings, 0)
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            BartButton(
                contentsId = R.string.bart_inflate_button_label,
                onClick = { /*TODO*/},
                modifier = Modifier.weight(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                .weight(1.2f)
            ) {
            }
            BartButton(
                contentsId = R.string.bart_collect_button_label,
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            )
        }
        
    }

}

@Composable
fun BartText(
    @StringRes textId: Int,
    money: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(
            text = stringResource(id = textId),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = "$${money}.00", // Updated eventually by viewModel
            style = MaterialTheme.typography.displaySmall
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

@Preview(
    showBackground = true,
    device = "spec:width=393dp,height=851dp,orientation=landscape"
)
@Composable
fun BartScreenPreview() {
    DanCognitionAppTheme {
        BartScreen()
    }
}
