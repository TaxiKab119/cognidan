package com.example.dancognitionapp.assessment.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.OptionCard
import com.example.dancognitionapp.utils.widget.navigateTo

class SelectionFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val isPractice = requireActivity().intent.getBooleanExtra(AssessmentActivity.IS_PRACTICE, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                if (isPractice) {
                    PracticeTestSelectScreen(
                        modifier = Modifier.fillMaxSize()
                    ) { destination ->
                        findNavController().navigate(destination)
                    }
                } else {
                    val viewModel:TrialDetailsViewModel = viewModel(factory = AppViewModelProvider.danAppViewModelFactory(false))
                    val uiState = viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
                    val action = SelectionFragmentDirections.actionSelectionDestToStartTrialDest(
                        trialDetails = TrialDetailsUiState(
                            selectedParticipant = uiState.value.selectedParticipant,
                            selectedTrialDay = uiState.value.selectedTrialDay,
                            selectedTrialTime = uiState.value.selectedTrialTime,
                        )
                    )
                    SelectTestDetailsScreen(
                        viewModel = viewModel,
                        participantList = uiState.value.participantList
                    ) {
                        findNavController().navigate(action)
                    }
                }
            }
        }
        return view
    }
}

@Composable
fun PracticeTestSelectScreen(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                DanCognitionTopAppBar(headerResId = R.string.selection_title)
            },
        ) {
            PracticeSelectionPageContent(
                modifier = modifier.padding(it),
                onClick = { dest ->
                    onClick(dest)
                }
            )
        }
    }
}

@Composable
fun PracticeSelectionPageContent(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        OptionCard(
            titleId = R.string.selection_practice_nback,
            modifier = Modifier
                .weight(1f)
                .navigateTo(R.id.nback_dest) {
                    onClick(it)
                }
        )
        OptionCard(
            titleId = R.string.selection_practice_bart,
            modifier = Modifier
                .weight(1f)
                .navigateTo(R.id.bart_dest) {
                    onClick(it)
                }
        )
    }
}
