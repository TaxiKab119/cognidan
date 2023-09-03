package com.example.dancognitionapp.assessment.nback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.assessment.AssessmentFragment

class NBackFragment: AssessmentFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val isPractice = requireActivity().intent.getBooleanExtra(AssessmentActivity.IS_PRACTICE, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            val viewModel: NBackViewModel = viewModel(factory = ViewModelFactory(isPractice))
            val uiState: NBackUiState by viewModel.uiState
            NBackScreen(isPractice, viewModel, uiState) {
                findNavController().popBackStack(R.id.selection_dest, inclusive = false)
            }
        }
        return view
    }
}
/**
 * Eventually this will change to the AppViewModelProvider
 * */
private class ViewModelFactory(private val isPractice: Boolean): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NBackViewModel(isPractice = isPractice) as T
    }
}