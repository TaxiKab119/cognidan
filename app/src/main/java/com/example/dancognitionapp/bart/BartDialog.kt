package com.example.dancognitionapp.bart


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.landing.LandingDestination
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import com.example.dancognitionapp.ui.widget.navigateTo


@Composable
fun BartDialog(
    showDialog: Boolean,
    @StringRes titleResId:Int,
    isTestComplete: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClick: (Int) -> Unit
 ) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                if(isTestComplete) {

                } else  {
                    onDismiss()
                }
            }
        ) {
            Card(
                modifier = Modifier
                    .navigateTo(R.id.selection_dest) {
                        if(isTestComplete) {
                            onClick(it)
                        }
                    }
            ) {
                Text(
                    text = stringResource(id = titleResId),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.CenterHorizontally)

                )
                Text(
                    text = stringResource(id = R.string.click_elsewhere_to_dismiss),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@LandscapePreview
@Composable
fun DialogPreview() {
    DanCognitionAppTheme {
        Surface(modifier=Modifier.fillMaxSize()) {
            BartDialog(true, R.string.balloon_popped_dialog_message, false, onDismiss = {}) {}
        }
    }
}
