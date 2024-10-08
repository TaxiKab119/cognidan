package com.example.dancognitionapp.utils.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R

@Composable
fun AssessmentInstructionsDialog(
    @StringRes assessmentTitle: Int?,
    modifier: Modifier = Modifier,
    showCancel: Boolean = true,
    onCancelClick: () -> Unit = {},
    onOkClick: () -> Unit = {},
    content: @Composable (() -> Unit) = {},
) {
    // Create a transparent background layer that covers the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = modifier
                .wrapContentSize()
                .fillMaxWidth(0.75f)
                .padding(16.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                assessmentTitle?.let {
                    Text(
                        text = stringResource(id = assessmentTitle),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                content()
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    if (showCancel) {
                        TextButton(
                            onClick = { onCancelClick() },
                            modifier = Modifier.padding(8.dp),
                            border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel_button),
                                modifier = Modifier.padding(6.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    TextButton(
                        onClick = { onOkClick() },
                        modifier = Modifier.padding(8.dp),
                        border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline),
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok_button),
                            modifier = Modifier.padding(6.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}