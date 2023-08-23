package com.example.dancognitionapp.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.edit.AddEditParticipantsFullScreen
import com.example.dancognitionapp.participants.edit.ParticipantScreenType
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

class ParticipantsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                val viewModel: ParticipantsViewModel = viewModel(factory = AppViewModelProvider.Factory)
                val uiState by viewModel.uiState.collectAsState()
                ParticipantManagerScreen(
                    uiState = uiState,
                    goToAddScreen = {
                        val action = ParticipantsFragmentDirections.actionParticipantsViewDestToAddModifyParticipantsViewDest()
                        findNavController().navigate(action)
                    },
                ) {
                    val action = ParticipantsFragmentDirections.actionParticipantsViewDestToAddModifyParticipantsViewDest(ParticipantScreenType.EDIT, participantInternalId = it)
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }
}