package com.example.dancognitionapp.assessment.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class StartTrialFragment: Fragment() {
    private val args: StartTrialFragmentArgs by navArgs()
    val viewModel by viewModels<StartTrialViewModel> { AppViewModelProvider.danAppViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val trialDetails = args.trialDetails
            lifecycleScope.launch(Dispatchers.IO) {
                Timber.i("check Trial existence was called")
                viewModel.checkTrialExistence(trialDetails ?: TrialDetailsUiState())
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val action = StartTrialFragmentDirections.actionStartTrialDestToNbackDest(
                trialDetails = TrialDetailsUiState(
                    selectedParticipant = args.trialDetails?.selectedParticipant,
                    selectedTrialDay = args.trialDetails?.selectedTrialDay,
                    selectedTrialTime = args.trialDetails?.selectedTrialTime,
                )
            )
            DanCognitionAppTheme {
                StartTrialScreen(
                    modifier = Modifier.fillMaxSize(),
                    participant = args.trialDetails?.selectedParticipant ?: Participant.emptyParticipant,
                    trialDay = args.trialDetails?.selectedTrialDay?.name ?: "error",
                    trialTime = args.trialDetails?.selectedTrialTime?.name ?: "error",
                    trialExistsStateFlow = viewModel.startTrialScreenState
                ) {
                    findNavController().navigate(action)
                }
            }

        }
        return view
    }
}
enum class StartTrialScreenState{
    EXISTS,
    NOT_EXIST,
    LOADING
}

@Composable
fun StartTrialScreen(
    modifier: Modifier = Modifier,
    participant: Participant,
    trialDay: String,
    trialTime: String,
    trialExistsStateFlow: StateFlow<StartTrialScreenState>,
    onFabClick: () -> Unit
) {
    val trialExists by trialExistsStateFlow.collectAsState()
    Timber.i("Screen Recomposed; Trial Exists: $trialExists")
    Scaffold(
        topBar = { DanCognitionTopAppBar(headerResId = R.string.selection_trial_details_header) },
        floatingActionButton = {
            if (trialExists == StartTrialScreenState.NOT_EXIST) {
                ExtendedFloatingActionButton(
                    onClick = { onFabClick() },
                    containerColor = MaterialTheme.colorScheme.secondary,
                ) {
                    Text(text = "Start")
                }
            }
        }
    ) {
        Column(
            modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (trialExists) {
                StartTrialScreenState.LOADING -> {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Loading")}
                else -> {
                    if (trialExists == StartTrialScreenState.EXISTS) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            ),
                            modifier = Modifier
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "warning",
                                    modifier = Modifier.padding(12.dp)
                                )
                                Text(
                                    text = "This trial already exists, please delete old data before starting"
                                )
                            }
                        }
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(24.dp)
                        ) {
                            Text("Participant Name: ${participant.name}")
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Participant ID: ${participant.userGivenId}")
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Trial Day: $trialDay")
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Trial Time: $trialTime")
                        }
                    }
                }
            }
        }
    }

}