package com.example.dancognitionapp.ui

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dancognitionapp.R
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPageScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            DanCognitionTopAppBar(headerResId = R.string.app_name)
        },
    ) {
        LandingPageContent(
            modifier = Modifier.padding(it)
        )
    }
}


@Composable
fun LandingPageContent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        OptionCard(
            titleId = R.string.landing_practice_trial,
            iconId = R.drawable.construction_48,
            onCardClick = {
                Timber.i("You clicked on Practice")
            },
            modifier = Modifier.weight(1f)
        )
        OptionCard(
            titleId = R.string.landing_participant_manager,
            iconId = R.drawable.groups_48,
            onCardClick = {
                Timber.i("You clicked on Participant Manager")
            },
            modifier = Modifier.weight(1f)
        )
        OptionCard(
            titleId = R.string.landing_start_trial,
            iconId = R.drawable.science_48,
            onCardClick = {
                Timber.i("You clicked on Start a Trial")
            },
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun OptionCard(
    @StringRes titleId: Int,
    @DrawableRes iconId: Int,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(12.dp),
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
                .clickable { onCardClick(titleId) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = stringResource(id = titleId),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(4.dp)
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DanCognitionTopAppBar(
    @StringRes headerResId: Int,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = headerResId),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}



@Preview(showBackground = true)
@Composable
fun OptionCardPreview() {
    DanCognitionAppTheme {
        OptionCard(
            titleId = R.string.landing_practice_trial,
            onCardClick = {},
            iconId = R.drawable.construction_48
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPageContentPreview() {
    DanCognitionAppTheme {
        LandingPageContent()
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPageScreenPreview() {
    DanCognitionAppTheme {
        LandingPageScreen()
    }
}