package com.example.dancognitionapp.assessment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

/**
 * Base class for all fragments that host an assessment. Contains behavior that all assessments will
 * implement
 */
abstract class AssessmentFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isPractice = requireActivity().intent.getBooleanExtra(AssessmentActivity.IS_PRACTICE, false)
        //blocks backpresses if this is a real assessment
        if (!isPractice) {
            val backPressedCallback = object: OnBackPressedCallback(isPractice) {
                override fun handleOnBackPressed() {}
            }
            requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}