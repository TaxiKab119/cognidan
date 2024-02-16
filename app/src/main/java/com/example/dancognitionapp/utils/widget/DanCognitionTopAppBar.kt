package com.example.dancognitionapp.utils.widget

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.landing.DanMenuItem
import com.example.dancognitionapp.participants.seetrialdata.DataDropDownMenu
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DanCognitionTopAppBar(
    @StringRes headerResId: Int,
    modifier: Modifier = Modifier,
    initialRotation: Float = 0f,
    wantMenuButton: Boolean = false,
    menuIcon: ImageVector = Icons.Default.MoreVert,
    menuItems: List<DanMenuItem> = listOf(),
    onMenuItemClick: (DanMenuItem) -> Unit = {}
) {
    var rotationState by remember { mutableStateOf(initialRotation) }
    var clickCounter by remember { mutableStateOf(0) }

    Timber.i("Rotation State: $rotationState")

    val rotation by animateFloatAsState(
        targetValue = rotationState,
        animationSpec = tween(durationMillis = 500), label = "spin"
    )
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = if (clickCounter >= 7) R.drawable.cognidan_flipped else R.drawable.brain_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp)
                        .graphicsLayer { rotationZ = rotation }
                        .clickable {
                            rotationState += 360f // Rotate by 360 degrees when clicked
                            clickCounter++
                            if (clickCounter >= 10) {
                                clickCounter = 0
                            }
                        }
                )
                ResponsiveText(
                    text = stringResource(id = headerResId),
                    targetTextSize = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        actions = {
            if (wantMenuButton) {
                var isMenuExpanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(
                            imageVector = menuIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    DataDropDownMenu(
                        isExpanded = isMenuExpanded,
                        onDismiss = { isMenuExpanded = false },
                        menuItems = menuItems
                    ) {
                        onMenuItemClick(it)
                        isMenuExpanded = false
                    }
                }
            }
        }
    )
}