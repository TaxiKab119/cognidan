package com.example.dancognitionapp.assessment.bart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.AppViewModelProvider
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme

class BartFragment: AssessmentFragment() {

    private val viewModel: BartViewModel by viewModels { AppViewModelProvider.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // pass participant id via navArgs
        viewModel.initBart(1, TrialDay.DAY_2, TrialTime.PRE_DIVE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                val uiState: BartUiState by viewModel.uiState.collectAsState(lifecycleScope.coroutineContext)
                BartTestScreen(
                    viewModel,
                    uiState,
                    Modifier.fillMaxSize()
                ) { destination ->
                    findNavController().popBackStack(destination, inclusive = false)
                }
            }
        }
        return view
    }
}