package com.example.dancognitionapp.participants.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.edit.ParticipantScreenType
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

class ParticipantsHomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                val viewModel: ParticipantsHomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
                val uiState by viewModel.uiState.collectAsState()
                ParticipantsHomeScreen(
                    participantList = uiState.participantList,
                    goToAddScreen = {
                        val action = ParticipantsHomeFragmentDirections.actionParticipantsViewDestToAddModifyParticipantsViewDest()
                        findNavController().navigate(action)
                    },
                ) {
                    val action = ParticipantsHomeFragmentDirections.actionParticipantsViewDestToAddModifyParticipantsViewDest(ParticipantScreenType.EDIT, participantInternalId = it)
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }
}