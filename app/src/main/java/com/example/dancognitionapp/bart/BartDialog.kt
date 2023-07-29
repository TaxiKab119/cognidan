package com.example.dancognitionapp.bart


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import com.example.dancognitionapp.ui.widget.navigateTo


@Composable
fun BartDialog(
    isTestComplete: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onClick: (Int) -> Unit
) {
    if (isTestComplete) {
        Dialog(onDismissRequest = { /*Do Nothing*/ }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigateTo(R.id.selection_dest) {
                        onClick(it)
                    }
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.end_of_test_dialog_message),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.click_to_leave),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(36.dp)
                    )
                }
            }
        }

    } else {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.error)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.balloon_popped_dialog_message),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.click_elsewhere_to_dismiss),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier
                            .padding(36.dp)
                    )
                }
            }
        }
    }
}

@LandscapePreview
@Composable
fun DialogPreview() {
    DanCognitionAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BartDialog(isTestComplete = true, onDismiss = {}) {}
        }
    }
}