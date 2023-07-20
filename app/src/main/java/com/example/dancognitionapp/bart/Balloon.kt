package com.example.dancognitionapp.bart

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

@Composable
fun Balloon(
    radius: Float,
    modifier: Modifier = Modifier
) {
    val verticalOffset = 100f
    val balloonColor = Color.Green
    val borderWidth = 4f

    val animatedRadius by animateFloatAsState(
        targetValue = radius,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val animatedHighlightRadius by animateFloatAsState(
        targetValue = radius / 4f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    Canvas (
        modifier = modifier
    ) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, balloonColor),
                center = Offset(
                    size.width / 2 - animatedHighlightRadius * 1.5f,
                    size.height - animatedHighlightRadius * 6f - verticalOffset
                ),
                radius = radius / 4f
            ),
            radius = animatedRadius,
            center = Offset(size.width / 2, size.height - animatedRadius - verticalOffset)
        )
        drawCircle(
            color = Color.Black,
            radius = animatedRadius,
            center = Offset(size.width / 2, size.height - animatedRadius - verticalOffset),
            style = Stroke(borderWidth)
        )
        drawRect(
            color = balloonColor,
            size = Size(40f, 60f),
            topLeft = Offset((size.width / 2) - 20f, size.height - verticalOffset - 10f)
        )
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 2 - 20f, size.height - 50f),
            end = Offset((size.width / 2) - 20f, size.height - verticalOffset - borderWidth),
            strokeWidth = borderWidth
        )
        drawLine(
            color = Color.Black,
            start = Offset(size.width / 2 + 20f, size.height - 50f),
            end = Offset((size.width / 2) + 20f, size.height - verticalOffset - borderWidth),
            strokeWidth = borderWidth
        )

        drawOval(
            color = balloonColor,
            size = Size(70f, 40f),
            topLeft = Offset((size.width / 2) - 35f, (size.height) - 60f)
        )
        drawArc(
            color = Color.Black,
            size = Size(70f, 40f),
            topLeft = Offset((size.width / 2) - 35f, (size.height) - 60f),
            useCenter = false,
            startAngle = -60f,
            sweepAngle = 300f,
            style = Stroke(borderWidth)

        )
    }
}

@LandscapePreview
@Composable
fun BalloonPreview() {
    DanCognitionAppTheme {
        Balloon(radius = 200f, modifier = Modifier.fillMaxSize())
    }
}