package com.example.dancognitionapp.assessment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.dancognitionapp.R

class AssessmentActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
    }

    companion object {
        const val IS_PRACTICE = "PRACTICE"

        fun newIntent(context: Context, isPractice: Boolean = false) =
            Intent(context, AssessmentActivity::class.java).putExtra(IS_PRACTICE, isPractice)
    }
}