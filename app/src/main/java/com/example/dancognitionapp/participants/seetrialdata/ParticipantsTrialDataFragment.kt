package com.example.dancognitionapp.participants.seetrialdata

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

class ParticipantsTrialDataFragment: Fragment() {
    private val args: ParticipantsTrialDataFragmentArgs by navArgs()
    val viewModel: ParticipantsTrialDataViewModel by viewModels { AppViewModelProvider.danAppViewModelFactory(false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            args.selectedParticipant?.let {
                viewModel.populateFields(it, it.id)
            }

            val participantId = args.selectedParticipant?.id ?: savedInstanceState?.getInt("participantId") ?: 0
            viewModel.populateFields(args.selectedParticipant ?: Participant.emptyParticipant, participantId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        args.selectedParticipant?.id?.let { outState.putInt("participantId", it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                ) {
                    viewModel.shareSelectedOrAll(requireContext(), it)
                }
            }
        }
        return view
    }
}