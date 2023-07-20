package com.example.dancognitionapp.assessment.selection

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import com.example.dancognitionapp.ui.widget.OptionCard
import com.example.dancognitionapp.ui.widget.navigateTo

class SelectionFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val isPractice = requireActivity().intent.getBooleanExtra(AssessmentActivity.IS_PRACTICE, false)
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            DanCognitionAppTheme {
                TestSelectScreen(
                    modifier = Modifier.fillMaxSize(),
                    isPractice = isPractice
                ) { destination ->
                    findNavController().navigate(destination)
                }
            }
        }
        return view
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSelectScreen(modifier: Modifier = Modifier, isPractice: Boolean = false, onClick: (Int) -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                DanCognitionTopAppBar(headerResId = R.string.selection_title)
            },
        ) {
            SelectionPageContent(
                modifier = modifier.padding(it),
                isPractice = isPractice,
                onClick = { dest ->
                    onClick(dest)
                }
            )
        }
    }
}

@Composable
fun SelectionPageContent(isPractice: Boolean, modifier: Modifier = Modifier, onClick: (Int) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        if (isPractice) {
            OptionCard(
                titleId = R.string.selection_practice_nback,
                modifier = Modifier
                    .weight(1f)
                    .navigateTo(R.id.nback_dest) {
                        onClick(it)
                    }
            )
            OptionCard(
                titleId = R.string.selection_practice_bart,
                modifier = Modifier
                    .weight(1f)
                    .navigateTo(R.id.bart_dest) {
                        onClick(it)
                    }
            )
        } else {
            //TODO - just BART test for now, but will be replaced with destinations that execute the full assessment
            OptionCard(
                titleId = R.string.selection_pre_dive,
                modifier = Modifier
                    .weight(1f)
                    .navigateTo(R.id.bart_dest) {

                    }
            )
            OptionCard(
                titleId = R.string.selection_dive,
                modifier = Modifier
                    .weight(1f)
                    .navigateTo(R.id.bart_dest) {

                    }
            )
            OptionCard(
                titleId = R.string.selection_post_dive,
                modifier = Modifier
                    .weight(1f)
                    .navigateTo(R.id.bart_dest) {

                    }
            )
        }
    }
}