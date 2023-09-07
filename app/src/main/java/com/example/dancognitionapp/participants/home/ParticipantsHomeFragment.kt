package com.example.dancognitionapp.participants.home

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
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.edit.ParticipantScreenType
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme

class ParticipantsHomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: ParticipantsHomeViewModel by viewModels { AppViewModelProvider.danAppViewModelFactory(false) }
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                val uiState by viewModel.uiState.collectAsState(context = lifecycleScope.coroutineContext)
                ParticipantsHomeScreen(
                    participantList = uiState.participantList,
                    goToAddScreen = {
                        val action = ParticipantsHomeFragmentDirections
                            .actionParticipantsViewDestToAddModifyParticipantsViewDest()
                        findNavController().navigate(action)
                    },
                ) {
                    val action = ParticipantsHomeFragmentDirections
                        .actionParticipantsViewDestToAddModifyParticipantsViewDest(
                            ParticipantScreenType.EDIT,
                            participantInternalId = it,
                        )
                    findNavController().navigate(action)
                }
            }
        }
        return view
    }
}