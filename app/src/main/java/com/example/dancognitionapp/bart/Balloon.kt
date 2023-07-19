package com.example.dancognitionapp.bart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import kotlin.math.roundToInt

@Composable
fun Balloon(
    balloonSizeX: Dp,
    balloonSizeY: Dp,
    modifier: Modifier = Modifier
) {
    val mainOvalWidth = balloonSizeX
    val mainOvalHeight = balloonSizeY

    // (x/260)  multiplier is based on "golden ratios" established in BalloonRatioBuilder
    val middleOvalWidth = (boxSize * 35.0/260).roundToInt()
    val middleOvalHeight = (boxSize * 31.0/260).roundToInt()

    val bottomOvalWidth = (boxSize * 35.0/260).roundToInt()
    val bottomOvalHeight = (boxSize * 17.0/260).roundToInt()

    val mainOffsetX = getOffsetXCoordinate(mainOvalWidth, boxSize)
    val mainOffsetY = getOffsetYCoordinate(
        mainOvalHeight,
        boxSize,
        1,
        middleOvalHeight
    )

    val middleOffsetX = getOffsetXCoordinate(middleOvalWidth, boxSize)
    val middleOffsetY = getOffsetYCoordinate(
        middleOvalHeight,
        boxSize,
        2,
        middleOvalHeight
    )

    val bottomOffsetX = getOffsetXCoordinate(bottomOvalWidth, boxSize)
    val bottomOffsetY = getOffsetYCoordinate(
        bottomOvalHeight,
        boxSize,
        3,
        middleOvalHeight
    )

    val inflatorWidth = (bottomOvalWidth * 0.7).roundToInt()
    val inflatorHeight = boxSize - (bottomOffsetY + bottomOvalHeight)

    val inflatorOffsetX = getOffsetXCoordinate(inflatorWidth, boxSize)
    val inflatorOffsetY = getOffsetYCoordinate(
        bottomOvalWidth,
        boxSize,
        4,
        middleOvalHeight
    )

    BoxWithConstraints() {
        Canvas(modifier = Modifier.padding(12.dp)) {
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, Color.Blue),
                    center = Offset(x = 90.dp.toPx(), y = 90.dp.toPx()),
                    radius = 26.dp.toPx()
                ),
                size = Size(
                    width = mainOvalWidth.dp.toPx(),
                    height = mainOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = mainOffsetX.dp.toPx(),
                    y = mainOffsetY.dp.toPx()
                )

            )
            drawOval(
                color = Color.Black,
                size = Size(
                    width = mainOvalWidth.dp.toPx(),
                    height = mainOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = mainOffsetX.dp.toPx(),
                    y = mainOffsetY.dp.toPx()
                ),
                style = Stroke(width = 1.dp.toPx())
            )
            drawOval(
                color = Color.Blue,
                size = Size(
                    width = middleOvalWidth.dp.toPx(),
                    height = middleOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = middleOffsetX.dp.toPx(),
                    y = middleOffsetY.dp.toPx()
                )
            )
            drawArc(
                color = Color.Black,
                size = Size(
                    width = middleOvalWidth.dp.toPx(),
                    height = middleOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = (middleOffsetX).dp.toPx(),
                    y = (middleOffsetY).dp.toPx()
                ),
                style = Stroke(width = 1.dp.toPx()),
                useCenter = false,
                startAngle = -20f,
                sweepAngle = 218f
            )
            /**Inflator Drawing*/
            drawRect(
                color = Color.DarkGray,
                size = Size(
                    width = inflatorWidth.dp.toPx(),
                    height = inflatorHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = inflatorOffsetX.dp.toPx(),
                    y = inflatorOffsetY.dp.toPx()
                )
            )
            drawOval(
                color = Color.Blue,
                size = Size(
                    width = bottomOvalWidth.dp.toPx(),
                    height = bottomOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = bottomOffsetX.dp.toPx(),
                    y = bottomOffsetY.dp.toPx()
                )
            )
            drawArc(
                color = Color.Black,
                size = Size(
                    width = bottomOvalWidth.dp.toPx(),
                    height = bottomOvalHeight.dp.toPx()
                ),
                topLeft = Offset(
                    x = bottomOffsetX.dp.toPx(),
                    y = bottomOffsetY.dp.toPx()
                ),
                style = Stroke(width = 1.dp.toPx()),
                useCenter = false,
                startAngle = -55f,
                sweepAngle = 290f
            )

        }
    }
}

private fun getOffsetXCoordinate(x: Int, canvasSize: Int): Int {
    return ((canvasSize / 2.0) - (x / 2.0)).roundToInt()
}

private fun getOffsetYCoordinate(
    y: Int,
    canvasSize: Int,
    ovalNumber: Int,
    middleOvalHeight: Int
): Int {
    val offset = when (ovalNumber) {
        1 -> ((canvasSize * 0.71 + (middleOvalHeight/2.0)) - y).roundToInt()
        2 -> (canvasSize * 0.71).roundToInt()
        3 -> (canvasSize * 0.81).roundToInt()
        else -> (canvasSize * 0.85).roundToInt()
    }
    return offset
}

fun getMaxOvalSizeAndIncrements(boxSize: Int, maxInflations: Int): Map<String, Int> {
    // Hardcoded ratios (i.e., 25.0/26) are based on "Golden Ratios" determined through trial&error
    val maxXVal = (boxSize * 25.0 / 26).roundToInt()
    val maxYVal = (boxSize * 10.0 / 13).roundToInt()

    val initXVal = (boxSize * 15.0 / 26).roundToInt()
    val initYVal = (boxSize * 6.0 / 13).roundToInt()

    return mapOf<String, Int>(
        "max_x" to maxXVal,
        "max_y" to maxYVal,
        "init_x" to initXVal,
        "init_y" to initYVal,
        "x_increment" to ((maxXVal - initXVal) / maxInflations.toDouble()).roundToInt(),
        "y_increment" to ((maxYVal - initYVal) / maxInflations.toDouble()).roundToInt(),
    )
}

@Composable
fun BalloonContainer(modifier: Modifier = Modifier, radius: Float) {
    BoxWithConstraints(modifier = modifier) {
        Balloon(balloonSizeX = maxWidth, balloonSizeY = maxHeight )
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=260dp,height=260dp"
)
@Composable
fun BalloonPreview() {
    DanCognitionAppTheme {
        Balloon(260, 150, 120)
    }
}