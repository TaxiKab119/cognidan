package com.example.dancognitionapp.utils.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ResponsiveText(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    text: String,
    targetTextSize: TextStyle
) {
    val typographySizes = listOf<TextStyle>(
        MaterialTheme.typography.displayLarge,
        MaterialTheme.typography.displayMedium,
        MaterialTheme.typography.displaySmall,
        MaterialTheme.typography.headlineLarge,
        MaterialTheme.typography.headlineMedium,
        MaterialTheme.typography.headlineSmall,
        MaterialTheme.typography.titleLarge,
        MaterialTheme.typography.titleMedium,
        MaterialTheme.typography.titleSmall,
        MaterialTheme.typography.bodyLarge,
        MaterialTheme.typography.bodyMedium,
        MaterialTheme.typography.bodySmall,
        MaterialTheme.typography.labelLarge,
        MaterialTheme.typography.labelMedium,
        MaterialTheme.typography.labelSmall,
    )

    var typographySizeIndex: Int by remember {
        mutableStateOf(typographySizes.indexOfFirst {it == targetTextSize})
    }
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult: TextLayoutResult ->
            val currentMaxLineIndex = textLayoutResult.lineCount - 1

            if (textLayoutResult.isLineEllipsized(currentMaxLineIndex)) {
                typographySizeIndex--
            }
        },
        style = try {
            typographySizes[typographySizeIndex]
        } catch (e: IndexOutOfBoundsException) {
            MaterialTheme.typography.labelSmall
        }
    )
}