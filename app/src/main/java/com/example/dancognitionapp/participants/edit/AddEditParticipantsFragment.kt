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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class AddEditParticipantsFragment: Fragment() {
    private val args: AddEditParticipantsFragmentArgs by navArgs()
    private val viewModel: AddEditViewModel by viewModels { AppViewModelProvider.factory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycleScope.launch {
            viewModel.populateParticipantFields(args.participantInternalId)
        }
        Timber.i("Agrs = participant internal Id: ${args.participantInternalId},Screen Type: ${args.screenType}")
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val uiState: AddEditUiState by viewModel.uiState.collectAsState(context = lifecycleScope.coroutineContext)
            DanCognitionAppTheme {
                AddEditParticipantsFullScreen(
                    screenType = args.screenType,
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