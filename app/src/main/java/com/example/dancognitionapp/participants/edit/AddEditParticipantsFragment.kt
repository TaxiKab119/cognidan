package com.example.dancognitionapp.participants.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
class AddEditParticipantsFragment: Fragment() {
    private val args: AddEditParticipantsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ParticipantIdManager.setParticipantId(args.participantInternalId)
        Timber.i("Agrs = participant internal Id: ${args.participantInternalId},Screen Type: ${args.screenType}")
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val addEditViewModel: AddEditViewModel = viewModel(factory = AppViewModelProvider.factory)
            val uiState: AddEditUiState by addEditViewModel.uiState.collectAsState()
            DanCognitionAppTheme {
                AddEditParticipantsFullScreen(
                    screenType = args.screenType,
                    viewModel = addEditViewModel,
                    uiState = uiState
                ) {
                    findNavController().popBackStack(R.id.participants_view_dest, false)
                }
            }
        }
        return view
    }

}

object ParticipantIdManager {
    private var participantInternalId: Int = 0

    fun setParticipantId(id: Int) {
        participantInternalId = id
    }

    fun getParticipantId(): Int {
        return participantInternalId
    }
}