package com.example.dancognitionapp.assessment.nback.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.selection.TrialDetailsUiState
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import timber.log.Timber

class NBackFragment : AssessmentFragment() {
    private val args: NBackFragmentArgs by navArgs()
    private val viewModel: NBackViewModel by viewModels {
        AppViewModelProvider.danAppViewModelFactory(
            isPractice
        )
    }

    private var isPractice: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPractice = args.trialDetails == null
        // this null check ensures that BART is only initialized/added to db once
        if (savedInstanceState == null) {
            initializeNBack(isPractice)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val uiState: NBackUiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
            val action = NBackFragmentDirections.actionNbackDestToBartDest(
                trialDetails = TrialDetailsUiState(
                    selectedParticipant = args.trialDetails?.selectedParticipant,
                    selectedTrialDay = args.trialDetails?.selectedTrialDay,
                    selectedTrialTime = args.trialDetails?.selectedTrialTime,
                )
            )
            DanCognitionAppTheme {
                NBackScreen(
                    isPractice,
                    viewModel,
                    uiState,
                    goToBart = {
                        findNavController().navigate(action)
                    },
                    returnToSelect = {
                        findNavController().popBackStack(R.id.selection_dest, inclusive = false)
                    }
                )
            }
        }
        return view
    }


    private fun initializeNBack(isPractice: Boolean) {
        if (isPractice) {
            // If isPractice we only call initNBackTrial to set maxLifetimePresentations = 3
            viewModel.initNBackTrial(
                Participant.emptyParticipant,
                TrialDay.DAY_1,
                TrialTime.PRE_DIVE,
                true
            )
            Timber.i("Practice NBack instantiated")
        } else {
            Timber.i("Real BART instantiated")
            // else, pass args via navArgs
            val participant = args.trialDetails?.selectedParticipant ?: Participant.emptyParticipant
            val trialDay = args.trialDetails?.selectedTrialDay ?: TrialDay.DAY_1
            val trialTime = args.trialDetails?.selectedTrialTime ?: TrialTime.PRE_DIVE
            Timber.i("ParticipantId: $participant\nTrialDay: $trialDay\nTrialTime: $trialTime")
            viewModel.initNBackTrial(participant, trialDay, trialTime, false)
        }
    }
}