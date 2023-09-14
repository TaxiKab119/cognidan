package com.example.dancognitionapp.participants.seetrialdata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.db.Participant
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber

class ParticipantsTrialDataFragment: Fragment() {
    private val args: ParticipantsTrialDataFragmentArgs by navArgs()
    val viewModel: ParticipantsTrialDataViewModel by viewModels { AppViewModelProvider.danAppViewModelFactory(false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            lifecycleScope.launch {
                viewModel.populateFields(args.selectedParticipant ?: Participant.emptyParticipant)
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
            DanCognitionAppTheme {
                val uiState by viewModel.uiState.collectAsState(context = lifecycleScope.coroutineContext)
                ParticipantsTrialDataScreen(
                    uiState = uiState,
                    viewModel = viewModel
                ) { _ ->
                    viewModel.downloadFiles(requireContext())
                }
            }
        }
        return view
    }
}