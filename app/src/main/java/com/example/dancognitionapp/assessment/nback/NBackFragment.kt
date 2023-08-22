package com.example.dancognitionapp.assessment.nback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.ui.nback.NBackScreen

class NBackFragment: AssessmentFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val isPractice = requireActivity().intent.getBooleanExtra(AssessmentActivity.IS_PRACTICE, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            NBackScreen(isPractice) {
                findNavController().popBackStack(R.id.selection_dest, inclusive = false)
            }
        }
        return view
    }
}