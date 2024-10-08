package com.example.dancognitionapp.assessment.dsst.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.di.AppViewModelProvider
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import timber.log.Timber

class DsstFragment : AssessmentFragment() {
    private val args: DsstFragmentArgs by navArgs()
    private val viewModel: DsstScreenViewModel by viewModels {
        AppViewModelProvider.danAppViewModelFactory(isPractice)
    }

    private var isPractice: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPractice = args.trialDetails == null

        if (savedInstanceState == null) {
            // TODO - Initialize the ViewModel (with isPractice field)
            initializeDsst(isPractice)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val details = args.trialDetails // TODO - Deal with these properly
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                DsstTestScreenRoot(
                    viewModel = viewModel,
                    onCancelClick =  {
                        findNavController().popBackStack(R.id.selection_dest, false)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        return view
    }

    private fun initializeDsst(isPractice: Boolean) {
        if (isPractice) {
            // If isPractice we call initDsst with empty values bc nothing written to db
            viewModel.initDsst(Participant.emptyParticipant, TrialDay.DAY_1, TrialTime.PRE_DIVE)
            Timber.i("Practice DSST instantiated")
        } else {
            Timber.i("Real DSST instantiated")
            // else, pass args via navArgs
            val participant = args.trialDetails?.selectedParticipant ?: Participant.emptyParticipant
            val trialDay = args.trialDetails?.selectedTrialDay ?: TrialDay.DAY_1
            val trialTime = args.trialDetails?.selectedTrialTime ?: TrialTime.PRE_DIVE
            Timber.i("ParticipantId: $\nTrialDay: $trialDay\nTrialTime: $trialTime")
            viewModel.initDsst(participant, trialDay, trialTime)
        }
    }
}