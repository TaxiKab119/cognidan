package com.example.dancognitionapp.participants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.dancognitionapp.R
import com.example.dancognitionapp.participants.data.Participant
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import timber.log.Timber

class AddModifyParticipantsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compose_host, container, false)
        val participant = arguments?.getParcelable<Participant?>("selectedParticipant")
        view.findViewById<ComposeView>(R.id.compose_root).setContent {
            Timber.i("$participant")
            DanCognitionAppTheme {
                AddModifyParticipantsScreen(
                    isModify = participant != null,
                    participant = participant
                )
            }
        }
        return view
    }
}