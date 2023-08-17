package com.example.dancognitionapp.ui.nback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.dancognitionapp.ui.LandscapePreview
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import kotlinx.coroutines.NonDisposableHandle.parent

//@Composable
//fun TicTacToeGrid() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        for (rowIndex in 0 until 3) {
//            if (rowIndex >= 1) {
//                Divider(color = Color.Black, thickness = 8.dp, modifier = Modifier.width(180.dp))
//            }
//            Row {
//                for (columnIndex in 0 until 3) {
//                    Box(
//                        modifier = Modifier
//                            .size(80.dp)
//                            .padding(4.dp),
//                        contentAlignment = Alignment.Center,
//                        content = {
//                            Box(
//                                modifier = Modifier
//                                    .size(60.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .background(MaterialTheme.colorScheme.background)
//                                    .padding(4.dp),
//                                contentAlignment = Alignment.Center,
//                                content = {
//                                    // You can customize the content of each cell here
//                                    Text(
//                                        text = "Hello",
//                                        color = MaterialTheme.colorScheme.primary
//                                    )
//                                }
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun TicTacToeGrid() {
//    Row(
//        modifier = Modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(Modifier) {
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Divider(color = Color.Black, thickness = 8.dp, modifier = Modifier.width(240.dp))
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//        }
//
//        Column(Modifier) {
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Divider(color = Color.Black, thickness = 8.dp, modifier = Modifier.width(240.dp))
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//        }
//
//        Column(Modifier) {
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//            Box(modifier = Modifier.size(80.dp)) {
//                Text(text = "Hello")
//            }
//        }
//    }
//}

@Composable
fun TicTacToeGrid() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (
            aBox,
            bBox,
            cBox,
            dBox,
            middleBox,
            eBox,
            fBox,
            gBox,
            hBox
        ) = createRefs()

        val topGuideline = createGuidelineFromTop(0.1f)
        val bottomGuideline = createGuidelineFromBottom(0.1f)
        val startGuideline = createGuidelineFromStart(0.3f)
        val endGuideline = createGuidelineFromEnd(0.3f)


        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Green)
            .wrapContentSize(Alignment.Center)
            .constrainAs(bBox) {
                top.linkTo(topGuideline)
                bottom.linkTo(middleBox.top)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
            }
        ) {
            Text("B")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Cyan)
            .wrapContentSize(Alignment.Center)
            .constrainAs(aBox) {
                top.linkTo(bBox.top)
                bottom.linkTo(bBox.bottom)
                start.linkTo(startGuideline)
                end.linkTo(middleBox.start)
            }
        ) {
            Text("A")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Blue)
            .wrapContentSize(Alignment.Center)
            .constrainAs(cBox) {
                top.linkTo(bBox.top)
                bottom.linkTo(bBox.bottom)
                start.linkTo(bBox.end)
                end.linkTo(endGuideline)
            }
        ) {
            Text("C")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Green)
            .wrapContentSize(Alignment.Center)
            .constrainAs(dBox) {
                top.linkTo(middleBox.top)
                bottom.linkTo(middleBox.bottom)
                start.linkTo(aBox.start)
                end.linkTo(aBox.end)
            }
        ) {
            Text("D")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Cyan)
            .wrapContentSize(Alignment.Center)
            .constrainAs(middleBox) {
                top.linkTo(topGuideline)
                bottom.linkTo(bottomGuideline)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
            }
        ) {
            Text("")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Blue)
            .wrapContentSize(Alignment.Center)
            .constrainAs(eBox) {
                top.linkTo(cBox.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(cBox.start)
                end.linkTo(cBox.end)
            }
        ) {
            Text("E")
        }
        Box(modifier = Modifier
            .size(80.dp)
            .background(color = Color.Blue)
            .wrapContentSize(Alignment.Center)
            .constrainAs(fBox) {
                top.linkTo(dBox.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(dBox.start)
                end.linkTo(dBox.end)
            }
        ) {
            Text("F")
        }
    }
}


@LandscapePreview
@Composable
fun TicTacToeGridPreview() {
    DanCognitionAppTheme {
        TicTacToeGrid()
    }
}