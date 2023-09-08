package com.example.dancognitionapp.participants.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class AddEditParticipantsFragment: Fragment() {
    private val args: AddEditParticipantsFragmentArgs by navArgs()
    private val viewModel: AddEditViewModel by viewModels { AppViewModelProvider.danAppViewModelFactory(false) }

    private var isNewParticipant: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isNewParticipant = args.selectedParticipant == null
        // this null check ensures that participant fields are only populated once
        if (savedInstanceState == null) {
            viewModel.populateParticipantFields(args.selectedParticipant)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val uiState: AddEditUiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
            DanCognitionAppTheme {
                AddEditParticipantsFullScreen(
                    screenType = if (isNewParticipant) ParticipantScreenType.ADD else ParticipantScreenType.EDIT,
                    viewModel = viewModel,
                    uiState = uiState
                ) {
                    findNavController().popBackStack(R.id.participants_view_dest, false)
                }
            }
        }
        return view
    }
}