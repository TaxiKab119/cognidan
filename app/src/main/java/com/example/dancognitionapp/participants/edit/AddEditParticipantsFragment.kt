package com.example.dancognitionapp.participants.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.di.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
            Timber.i("Selected Participant: ${args.selectedParticipant}")
            viewModel.populateParticipantFields(args.selectedParticipant)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val uiState: AddEditUiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
            var isLoading: Boolean by remember { mutableStateOf(false) }
            DanCognitionAppTheme {
                if (isLoading) {
                    AlertDialog(
                        onDismissRequest = { /*Not Dismissible*/ },
                    ) {
                        Text("Please wait...")
                    }
                }
                AddEditParticipantsFullScreen(
                    screenType = if (isNewParticipant) ParticipantScreenType.ADD else ParticipantScreenType.EDIT,
                    viewModel = viewModel,
                    uiState = uiState,
                    onConfirm = {
                        isLoading = true
                        lifecycleScope.launch(Dispatchers.IO) {
                            val delete = async(Dispatchers.IO) {
                                viewModel.deleteParticipant()
                            }
                            delete.await()
                        }
                        findNavController().popBackStack(R.id.participants_view_dest, false)
                    }
                ) {
                    findNavController().popBackStack(R.id.participants_view_dest, false)
                }
            }
        }
        return view
    }
}