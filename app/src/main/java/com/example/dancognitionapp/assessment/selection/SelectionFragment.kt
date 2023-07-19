package com.example.dancognitionapp.assessment.selection

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
import com.example.dancognitionapp.Destination
import com.example.dancognitionapp.R
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.ui.landing.DanCognitionTopAppBar
import com.example.dancognitionapp.ui.landing.OptionCard
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme

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
                    when(destination) {
                        Destination.BART -> findNavController().navigate(R.id.bart_dest)
                        Destination.NBack -> findNavController().navigate(R.id.nback_dest)
                        else -> {}
                    }
                }
            }
        }
        return view
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSelectScreen(modifier: Modifier = Modifier, isPractice: Boolean = false, onClick: (Destination) -> Unit = {}) {
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
fun SelectionPageContent(isPractice: Boolean, modifier: Modifier = Modifier, onClick: (Destination) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        if (isPractice) {
            OptionCard(
                titleId = R.string.selection_practice_nback,
                destination = Destination.NBack,
                onCardClick = {
                    onClick(it)
                },
                modifier = Modifier.weight(1f)
            )
            OptionCard(
                titleId = R.string.selection_practice_bart,
                destination = Destination.BART,
                onCardClick = {
                    onClick(it)
                },
                modifier = Modifier.weight(1f)
            )
        } else {
            OptionCard(
                titleId = R.string.selection_pre_dive,
                destination = Destination.PreDive,
                onCardClick = {
                    onClick(it)
                },
                modifier = Modifier
                    .weight(1f)
            )
            OptionCard(
                titleId = R.string.selection_dive,
                destination = Destination.Dive,
                onCardClick = {
                    onClick(it)
                },
                modifier = Modifier
                    .weight(1f)
            )
            OptionCard(
                titleId = R.string.selection_post_dive,
                destination = Destination.PostDive,
                onCardClick = {
                    onClick(it)
                },
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}