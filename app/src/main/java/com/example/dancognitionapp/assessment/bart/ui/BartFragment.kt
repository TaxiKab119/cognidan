package com.example.dancognitionapp.assessment.bart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.di.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.ui.components.BartInstructionDialog
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import timber.log.Timber

class BartFragment: AssessmentFragment() {
    private val navArgs: BartFragmentArgs by navArgs()
    private val viewModel: BartViewModel by viewModels { AppViewModelProvider.danAppViewModelFactory(isPractice) }

    private var isPractice: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPractice = navArgs.trialDetails == null
        // this null check ensures that BART is only initialized/added to db once
        if (savedInstanceState == null) {
            initializeBart(isPractice)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                val uiState: BartUiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
                if (!uiState.hasTestBegun) {
                    BartInstructionDialog(
                        isPractice = isPractice,
                        onCancelClick = {
                            findNavController().popBackStack(R.id.selection_dest, false)
                        }
                    ) {
                        viewModel.hideInstructions()
                    }
                } else {
                    BartTestScreen(
                        viewModel,
                        uiState,
                        Modifier.fillMaxSize()
                    ) { destination ->
                        findNavController().popBackStack(destination, inclusive = false)
                    }
                }

            }
        }
        return view
    }
    private fun initializeBart(isPractice: Boolean) {
        if (isPractice) {
            // If isPractice we call initBart with empty values bc nothing written to db
            viewModel.initBart(Participant.emptyParticipant, TrialDay.DAY_1, TrialTime.PRE_DIVE)
            Timber.i("Practice BART instantiated")
        } else {
            Timber.i("Real BART instantiated")
            // else, pass args via navArgs
            val participant = navArgs.trialDetails?.selectedParticipant ?: Participant.emptyParticipant
            val trialDay = navArgs.trialDetails?.selectedTrialDay ?: TrialDay.DAY_1
            val trialTime = navArgs.trialDetails?.selectedTrialTime ?: TrialTime.PRE_DIVE
            Timber.i("ParticipantId: $\nTrialDay: $trialDay\nTrialTime: $trialTime")
            viewModel.initBart(participant, trialDay, trialTime)
        }
    }

}

