package com.example.dancognitionapp.bart

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import kotlin.math.roundToInt

@Composable
fun Balloon(
    boxSize: Int,
    balloonSizeX: Int = 150,
    balloonSizeY: Int = 120,
    modifier: Modifier = Modifier
) {
    val mainOvalWidth = balloonSizeX
    val mainOvalHeight = balloonSizeY

    // (x/360)  multiplier is based on "golden ratios" established in BalloonRatioBuilder
    val middleOvalWidth = (boxSize * 35/260)
    val middleOvalHeight = (boxSize * 31/260)

    val bottomOvalWidth = (boxSize * 35/260)
    val bottomOvalHeight = (boxSize * 17/260)

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

    Box(
        modifier = modifier
            .size(size = boxSize.dp)
            .drawBehind {
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
    )
}

fun getOffsetXCoordinate(x: Int, canvasSize: Int): Int {
    return ((canvasSize / 2.0) - (x / 2.0)).roundToInt()
}

fun getOffsetYCoordinate(
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

@Preview(
    showBackground = true,
    device = "spec:width=260dp,height=260dp"
)
@Composable
fun BalloonPreview() {
    DanCognitionAppTheme {
        Balloon(260)
    }
}