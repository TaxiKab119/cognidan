package com.example.dancognitionapp.assessment.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.R
import com.example.dancognitionapp.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme

class StartTrialFragment: Fragment() {
    private val args: StartTrialFragmentArgs by navArgs()

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
                    participantName = args.trialDetails?.selectedParticipant?.name ?: "error",
                    trialDay = args.trialDetails?.selectedTrialDay?.name ?: "error",
                    trialTime = args.trialDetails?.selectedTrialTime?.name ?: "error"
                ) {
                    findNavController().navigate(action)
                }
            }

        }
        return view
    }
}

@Composable
fun StartTrialScreen(
    modifier: Modifier = Modifier,
    participantName: String,
    trialDay: String,
    trialTime: String,
    onFabClick: () -> Unit
) {
    Scaffold(
        topBar = { DanCognitionTopAppBar(headerResId = R.string.selection_trial_details_header) },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = { onFabClick() }) {
                Text(text = "OK")
            }
        }
    ) {
        Column(
            modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("You selected: $participantName")
            Spacer(modifier = Modifier.height(12.dp))
            Text("You selected: $trialDay")
            Spacer(modifier = Modifier.height(12.dp))
            Text("You selected: $trialTime")
        }
    }

}