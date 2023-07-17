package com.example.dancognitionapp.bart

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 0.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = "Reward for balloon:",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "0$",
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Column(modifier = Modifier) {
                Text(
                    text = "Total earnings:",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "0$",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
        ) {
            BartButton(
                contentsId = R.string.bart_inflate_button_label,
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            )
            Balloon(260)
            BartButton(
                contentsId = R.string.bart_collect_button_label,
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            )
        }
        
    }

}

@Composable
fun BartButton(
    @StringRes contentsId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClick },
        modifier
            .fillMaxSize()
            .padding(12.dp, vertical = 24.dp)
    ) {
        Text(
            text = stringResource(id = contentsId),
            style = MaterialTheme.typography.displayMedium
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
