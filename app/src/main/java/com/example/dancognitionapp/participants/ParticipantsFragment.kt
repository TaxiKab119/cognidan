package com.example.dancognitionapp.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dancognitionapp.R
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
                var showAddModifyScreen by remember { mutableStateOf(false) }
                val viewModel: ParticipantsViewModel = viewModel()
                val uiState by viewModel.uiState
                if (showAddModifyScreen) {
                    AddEditParticipantsFullScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        screenType = uiState.isAddOrEdit
                    ) { showAddModifyScreen = false }
                } else {
                    ParticipantManagerScreen(
                        viewModel = viewModel,
                        uiState = uiState
                    ) { showAddModifyScreen = true }
                }
            }
        }
        return view
    }
}