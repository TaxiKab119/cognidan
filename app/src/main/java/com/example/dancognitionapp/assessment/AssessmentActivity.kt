package com.example.dancognitionapp.assessment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.example.dancognitionapp.R

class AssessmentActivity: FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_assessment)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    companion object {
        const val IS_PRACTICE = "PRACTICE"

        fun newIntent(context: Context, isPractice: Boolean = false) =
            Intent(context, AssessmentActivity::class.java).putExtra(IS_PRACTICE, isPractice)
    }
}