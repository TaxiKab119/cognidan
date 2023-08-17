package com.example.dancognitionapp.ui.nback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import java.lang.Math.random
import kotlin.random.Random

//@Composable
//fun NBackScreen(uiState: Char) {
//    ConstraintLayout(Modifier.fillMaxSize()) {
//        val stimuli = listOf('A', 'B', 'C', 'D','Z', 'E', 'F', 'G', 'H')
//
//        val rowCount = 3
//        val columnCount = 3
//        val (
//            aBox,
//            bBox,
//            cBox,
//            dBox,
//            middleBox,
//            eBox,
//            fBox,
//            gBox,
//            hBox
//        ) = createRefs()
//
//        val topGuideline = createGuidelineFromTop(0.1f)
//        val bottomGuideline = createGuidelineFromBottom(0.1f)
//        val startGuideline = createGuidelineFromStart(0.3f)
//        val endGuideline = createGuidelineFromEnd(0.3f)
//
//        val allRefs = listOf(aBox, bBox, cBox, dBox, middleBox, eBox, fBox, gBox, hBox)
//        NBackQuadrant(
//            quadrantChar = 'Z',
//            modifier = Modifier
//                .constrainAs(middleBox) {
//                    top.linkTo(topGuideline)
//                    bottom.linkTo(bottomGuideline)
//                    start.linkTo(startGuideline)
//                    end.linkTo(endGuideline)
//                }
//        )
//
//        for (i in 0 until rowCount) {
//            for (j in 0 until columnCount) {
//                val listIndex = i * columnCount + j
//                if (listIndex == 4) {
//                    continue // Middle Box is already generated
//                }
//
//                val stimulus = stimuli[listIndex]
//                val ref = allRefs[listIndex]
//
//
//                val (toplink, bottomLink) = when {
//                    listIndex <= 2 -> topGuideline to middleBox.top
//                    listIndex <= 5 -> middleBox.top to middleBox.bottom
//                    else -> middleBox.bottom to bottomGuideline
//                }
//                val (startLink, endLink) = when (listIndex) {
//                    in listOf(0, 3, 6) -> startGuideline to middleBox.start
//                    in listOf(1, 4, 2) -> middleBox.start to middleBox.end
//                    else -> middleBox.end to endGuideline
//                }
////                NBackQuadrant(
////                    quadrantChar = stimulus,
////                    modifier = Modifier.constrainAs(ref) {
////                        top.linkTo(toplink)
////                        bottom.linkTo(bottomLink)
////                        start.linkTo(startLink)
////                        end.linkTo(endLink)
////                    }
////                )
//            }
//        }
//    }
//}

//@Composable
//fun NBackScreen(uiState: Char) {
//    val currentChar by remember { mutableStateOf('A')}
//
//    ConstraintLayout(Modifier.fillMaxSize()) {
//        val stimuli = listOf('A', 'B', 'C', 'D','Z', 'E', 'F', 'G', 'H')
//
//        val rowCount = 3
//        val columnCount = 3
//        val (
//            aBox,
//            bBox,
//            cBox,
//            dBox,
//            middleBox,
//            eBox,
//            fBox,
//            gBox,
//            hBox
//        ) = createRefs()
//
//        val topGuideline = createGuidelineFromTop(0.1f)
//        val bottomGuideline = createGuidelineFromBottom(0.1f)
//        val startGuideline = createGuidelineFromStart(0.3f)
//        val endGuideline = createGuidelineFromEnd(0.3f)
//
//        val allRefs = listOf(aBox, bBox, cBox, dBox, middleBox, eBox, fBox, gBox, hBox)
//        NBackQuadrant(
//            currentChar = currentChar,
//            quadrantChar = 'Z',
//            modifier = Modifier
//                .constrainAs(middleBox) {
//                    top.linkTo(topGuideline)
//                    bottom.linkTo(bottomGuideline)
//                    start.linkTo(startGuideline)
//                    end.linkTo(endGuideline)
//                }
//        )
//        NBackQuadrant(
//            currentChar = currentChar,
//            quadrantChar = stimuli[0],
//            modifier = Modifier.constrainAs(allRefs[0]) {
//                top.linkTo(topGuideline)
//                bottom.linkTo(middleBox.top)
//                start.linkTo(startGuideline)
//                end.linkTo(middleBox.start)
//            }
//        )
//        NBackQuadrant(
//            currentChar = currentChar,
//            quadrantChar = stimuli[0],
//            modifier = Modifier.constrainAs(allRefs[0]) {
//                top.linkTo(topGuideline)
//                bottom.linkTo(middleBox.top)
//                start.linkTo(startGuideline)
//                end.linkTo(middleBox.start)
//            }
//        )
//
//        for (i in 0 until rowCount) {
//            for (j in 0 until columnCount) {
//                val listIndex = i * columnCount + j
//                if (listIndex == 4) {
//                    continue // Middle Box is already generated
//                }
//
//                val stimulus = stimuli[listIndex]
//                val ref = allRefs[listIndex]
//
//
//                val (toplink, bottomLink) = when {
//                    listIndex <= 2 -> topGuideline to middleBox.top
//                    listIndex <= 5 -> middleBox.top to middleBox.bottom
//                    else -> middleBox.bottom to bottomGuideline
//                }
//                val (startLink, endLink) = when (listIndex) {
//                    in listOf(0, 3, 6) -> startGuideline to middleBox.start
//                    in listOf(1, 4, 2) -> middleBox.start to middleBox.end
//                    else -> middleBox.end to endGuideline
//                }
//                NBackQuadrant(
//                    currentChar = currentChar,
//                    quadrantChar = stimulus,
//                )
//            }
//        }
//    }
//}


@Composable
fun NBackQuadrant(modifier: Modifier = Modifier, currentChar: Char = 'a', quadrantChar: Char) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .border(8.dp, Color.Black)
            .size(85.dp)
    ) {
        if (currentChar == quadrantChar) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(Color.Blue)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun NBackScreen(uiState: Char) {
    var currentChar by remember { mutableStateOf('A') }
    val stimuli = listOf('A', 'B', 'C', 'D', 'Z', 'E', 'F', 'G', 'H')
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { currentChar = stimuli.random() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentSize(Alignment.Center)
                .border(8.dp, color = MaterialTheme.colorScheme.surface)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(stimuli.size) { index ->
                    val stimulus = stimuli[index]
                    NBackQuadrant(
                        currentChar = currentChar,
                        quadrantChar = stimulus
                    )
                }
            }
        }
    }
}


@LandscapePreview
@Composable
fun NBackScreenPreview() {
    DanCognitionAppTheme {
        NBackScreen('B')
    }
}