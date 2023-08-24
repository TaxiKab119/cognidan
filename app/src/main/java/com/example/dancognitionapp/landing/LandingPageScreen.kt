package com.example.dancognitionapp.landing

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.OptionCard
import com.example.dancognitionapp.utils.widget.navigateTo
import timber.log.Timber

enum class LandingDestination {
    StartTrial,
    Practice,
    AddParticipants,
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPageScreen(modifier: Modifier = Modifier, onClick: (LandingDestination) -> Unit = {}) {
    Scaffold(
        topBar = {
            DanCognitionTopAppBar(headerResId = R.string.app_name)
        },
    ) {
        LandingPageContent(
            modifier = Modifier.padding(it),
            onClick = { titleId ->
                onClick(titleId)
                Timber.i("You clicked $titleId")
            }
        )
    }
}

@Composable
fun LandingPageContent(modifier: Modifier = Modifier, onClick: (LandingDestination) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        OptionCard(
            titleId = R.string.landing_practice_trial,
            iconId = R.drawable.construction_48,
            modifier = Modifier
                .weight(1f)
                .navigateTo(LandingDestination.Practice) {
                    onClick(it)
                }
        )
        OptionCard(
            titleId = R.string.landing_participant_manager,
            iconId = R.drawable.groups_48,
            modifier = Modifier
                .weight(1f)
                .navigateTo(LandingDestination.AddParticipants) {
                    onClick(it)
                }
        )
        OptionCard(
            titleId = R.string.landing_start_trial,
            iconId = R.drawable.science_48,
            modifier = Modifier
                .weight(1f)
                .navigateTo(LandingDestination.StartTrial) {
                    onClick(it)
                }
        )
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
            iconId = R.drawable.construction_48,
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