package com.example.dancognitionapp.assessment.bart.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.dancognitionapp.utils.LandscapePreview
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.AssessmentInstructionsDialog
import com.example.dancognitionapp.utils.widget.ResponsiveText


@Composable
fun BartDialog(
    isTestComplete: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
            ) {
                val message = if (isTestComplete) {
                    R.string.bart_dialog_end_message
                } else {
                    R.string.bart_dialog_popped_message
                }
                val instructions = if (isTestComplete) {
                    R.string.bart_dialog_end_instructions
                } else {
                    R.string.bart_dialog_popped_instructions
                }
                Text(
                    text = stringResource(id = message),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(24.dp)
                )
                Text(
                    text = stringResource(id = instructions),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(36.dp)
                        .clickable {
                            onDismiss()
                        }
                )
            }
        }
    }
}

@Composable
fun BartInstructionDialog(
    isPractice: Boolean,
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit = {},
    onOkClick: () -> Unit = {}
) {
    AssessmentInstructionsDialog(
        assessmentTitle = R.string.bart_name_expanded,
        onOkClick = onOkClick,
        onCancelClick = onCancelClick,
        showCancel = isPractice,
        modifier = modifier
    ) {
        ResponsiveText(
            text = stringResource(id = R.string.bart_test_instructions),
            maxLines = 6,
            targetTextSize = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@LandscapePreview
@Composable
fun DialogPreview() {
    DanCognitionAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BartDialog(isTestComplete = true, onDismiss = {})
        }
    }
}

@LandscapePreview
@Composable
fun InstructionDialogPreview() {
    DanCognitionAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            BartInstructionDialog(true)
        }
    }
}