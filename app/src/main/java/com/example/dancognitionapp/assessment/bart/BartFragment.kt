package com.example.dancognitionapp.assessment.bart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dancognitionapp.R

class BartFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bart, container, false)
        view.findViewById<ComposeView>(R.id.bart_compose_root).setContent {
            Text(text = "Put BART screen here")
        }
        return view
    }
}