package com.example.dancognitionapp.assessment.bart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentFragment
import com.example.dancognitionapp.bart.BartTestScreen
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

class BartFragment: AssessmentFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme() {
                BartTestScreen(Modifier.fillMaxSize()) { destination ->
                    findNavController().navigate(destination)
                }
            }
        }
        return view
    }
}