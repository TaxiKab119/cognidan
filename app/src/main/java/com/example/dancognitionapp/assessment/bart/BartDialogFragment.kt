package com.example.dancognitionapp.assessment.bart

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.dancognitionapp.R
import com.example.dancognitionapp.bart.BartViewModel

class BartDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_bart_dialog)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Find the TextView to display the message
        val dialogText = view.findViewById<TextView>(R.id.bartDialogTitle)
        val message = arguments?.getInt(BART_MESSAGE)
        message?.let {
            // Set the message to the TextView
            dialogText.text = getString(it)
        }
    }

    companion object {
        const val BART_MESSAGE = "BART_MESSAGE"
        fun newInstance(@StringRes message: Int): BartDialogFragment {
            val bundle = Bundle().apply {
                putInt(BART_MESSAGE, message)
            }
            return BartDialogFragment().apply {
                arguments = bundle
            }
        }
    }
}
