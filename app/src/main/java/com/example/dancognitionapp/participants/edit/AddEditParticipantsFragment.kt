package com.example.dancognitionapp.participants.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.DanCognitionApplication
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.ParticipantRepository
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
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
            val addEditViewModel: AddEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
            DanCognitionAppTheme {
                AddEditParticipantsFullScreen(
                    screenType = args.screenType,
                    viewModel = addEditViewModel,
                    uiState = addEditViewModel.uiState.value
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