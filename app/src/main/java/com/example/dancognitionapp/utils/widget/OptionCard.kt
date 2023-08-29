package com.example.dancognitionapp.utils.widget

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.landing.LandingDestination

@Composable
fun OptionCard(
    @StringRes titleId: Int,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int = 0) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                if (iconId != 0) {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = stringResource(id = titleId),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.size(24.dp))
                Text(
                    text = stringResource(id = titleId),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun Modifier.navigateTo(destination: LandingDestination, onClick: (LandingDestination) -> Unit) =
    this.padding(12.dp).clickable { onClick(destination) }

@Composable
fun Modifier.navigateTo(@IdRes destinationId: Int, onClick: (Int) -> Unit) =
    this.padding(12.dp).clickable { onClick(destinationId) }