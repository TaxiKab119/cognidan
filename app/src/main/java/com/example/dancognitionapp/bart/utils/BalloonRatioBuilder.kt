package com.example.dancognitionapp.bart.utils

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

/**
 * DON"T WORRY ABOUT THIS RIGHT NOW, THIS WAS JUST TO HELP ME GET
 * THE "GOLDEN NUMBERS" TO HAVE THE PROPER BALLOON SHAPE REGARDLESS OF
 * BOX SIZE... 260 IS THE SIZE I'VE USED...
 *
 * we can get rid of this code later but in case I need to change the box size this is helpful...
 * */

val balloonNumbers300: List<Int> = listOf(90, 90, 30, 200, 220, 50, 10, 200, 220, 50, 10, 2, 40, 36,
    130, 210, 40, 36, 130, 209, 2, 40, 20, 130, 240, 40, 20, 130, 240, 2)

val balloonNumbers260: List<Int> = listOf(78, 78, 26, 173, 191, 43, 9, 173, 191, 43, 9, 2, 35, 31,
    113, 182, 35, 31, 113, 181, 2, 35, 17, 113, 208, 35, 17, 113, 208, 2)

val balloonNumbers260Test: List<Int> = listOf(78, 78, 26, 173, 191, 43, 9, 173, 191, 43, 9, 2, 35, 31,
    113, 182, 35, 31, 113, 181, 2, 35, 17, 113, 208, 35, 17, 113, 208, 2)

val tenthBalloonNumbers = scaledList(balloonNumbers300, 0.1)

@Composable
fun BalloonTest(values: List<Int>, size: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size = size.dp)
            .drawBehind {
                drawOval(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, Color.Blue),
                        center = Offset(x = (values[0]).dp.toPx(), y = (values[1]).dp.toPx()),
                        radius = (values[2]).dp.toPx()
                    ),
                    size = Size(width = (values[3]).dp.toPx(), height = (values[4]).dp.toPx()),
                    topLeft = Offset(x = (values[5]).dp.toPx(), y = (values[6]).dp.toPx())

                )
                drawOval(
                    color = Color.Black,
                    size = Size(width = (values[7]).dp.toPx(), height = (values[8]).dp.toPx()),
                    topLeft = Offset(x = (values[9]).dp.toPx(), y = (values[10]).dp.toPx()),
                    style = Stroke(width = (values[11]). dp.toPx())

                )
                drawOval(
                    color = Color.Blue,
                    size = Size(width = (values[12]) .dp.toPx(), height = (values[13]) .dp.toPx()),
                    topLeft = Offset(x = (values[14]).dp.toPx(), y = (values[15]).dp.toPx())
                )
                drawArc(
                    color = Color.Black,
                    size = Size(width = (values[16]) .dp.toPx(), height = (values[17]) .dp.toPx()),
                    topLeft = Offset(x = (values[18]).dp.toPx(), y = (values[19]).dp.toPx()),
                    style = Stroke(width = (values[20]). dp.toPx()),
                    useCenter = false,
                    startAngle = -5f,
                    sweepAngle = 190f
                )
                drawOval(
                    color = Color.Blue,
                    size = Size(width = (values[21]) .dp.toPx(), height = (values[22]) .dp.toPx()),
                    topLeft = Offset(x = (values[23]).dp.toPx(), y = (values[24]).dp.toPx())
                )
                drawArc(
                    color = Color.Black,
                    size = Size(width = (values[25]) .dp.toPx(), height = (values[26]) .dp.toPx()),
                    topLeft = Offset(x = (values[27]).dp.toPx(), y = (values[28]).dp.toPx()),
                    style = Stroke(width = (values[29]). dp.toPx()),
                    useCenter = false,
                    startAngle = -55f,
                    sweepAngle = 290f
                )
            }
    )
}

fun scaledList(list: List<Int>, scalingFactor: Double): List<Int> {
    val scaledNums: MutableList<Int> = mutableListOf()

    list.forEach{
        var value = (it * scalingFactor).roundToInt()
        if(value < 1) {
            value = 1
        }
        scaledNums.add(value)
    }

    return scaledNums
}

fun scaledListForBox(list: List<Int>, desiredBoxSize: Int): List<Int> {
    val scaledNums: MutableList<Int> = mutableListOf()

    list.forEach {
        val ratio = (1.0/(300.toFloat()/260.toFloat()))
        val final = (ratio * it).roundToInt()
        scaledNums.add(final)
    }

    return scaledNums
}

@Preview(showBackground = true)
@Composable
fun BalloonNormalPreview() {
    DanCognitionAppTheme {
        BalloonTest(balloonNumbers260Test, 260)
    }
}

@Preview(showBackground = true)
@Composable
fun BalloonScaledPreview() {
    DanCognitionAppTheme {
        BalloonTest(tenthBalloonNumbers, 260)
    }
}