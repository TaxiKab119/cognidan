package com.example.dancognitionapp.assessment.dsst.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.di.AppViewModelProvider
import com.example.dancognitionapp.utils.theme.DanCognitionAppTheme

class DsstFragment : AssessmentFragment() {
    private val args: DsstFragmentArgs by navArgs()
    private val viewModel: DsstScreenViewModel by viewModels {
        AppViewModelProvider.danAppViewModelFactory(isPractice)
    }

    private var isPractice: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPractice = args.trialDetails == null

        if (savedInstanceState == null) {
            // TODO - Initialize the ViewModel (with isPractice field)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val details = args.trialDetails // TODO - Deal with these properly
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                DsstTestScreenRoot(viewModel = viewModel)
            }
        }
        return view
    }
}