package com.example.dancognitionapp.assessment.selection

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.di.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import com.example.dancognitionapp.utils.widget.DanCognitionTopAppBar
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
                        modifier = Modifier.fillMaxSize(),
                        onBackPress = {
                            requireActivity().onBackPressedDispatcher.onBackPressed()
                        }
                    ) { destination ->
                        findNavController().navigate(destination)
                    }
                } else {
                    val viewModel:TrialDetailsViewModel = viewModel(factory = AppViewModelProvider.danAppViewModelFactory())
                    val uiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
                    val action = SelectionFragmentDirections.actionSelectionDestToStartTrialDest(
                        trialDetails = TrialDetailsUiState(
                            selectedParticipant = uiState.selectedParticipant,
                            selectedTrialDay = uiState.selectedTrialDay,
                            selectedTrialTime = uiState.selectedTrialTime,
                        )
                    )
                    SelectTestDetailsScreen(
                        viewModel = viewModel,
                        participantList = uiState.participantList,
                        uiState = uiState,
                        onBackPress = { requireActivity().onBackPressedDispatcher.onBackPressed() },
                        onSelectTrialTime = { viewModel.selectTrialTime(it) },
                        onSelectTrialDay = { viewModel.selectTrialDay(it) },
                        onSelectParticipant = { viewModel.selectParticipant(it) }
                    ) {
                        findNavController().navigate(action)
                    }
                }
            }
        }
        return view
    }
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

}

@Composable
fun PracticeTestSelectScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    onClick: (Int) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                DanCognitionTopAppBar(
                    headerResId = R.string.selection_title,
                    wantBackButton = true
                ) {
                    onBackPress()
                }
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
        OptionCard(
            titleId = R.string.dsst_screen_title,
            modifier = Modifier
                .weight(1f)
                .navigateTo(R.id.dsst_dest) {
                    onClick(it)
                }
        )
    }
}
