package com.example.dancognitionapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dancognitionapp.assessment.AssessmentActivity
import com.example.dancognitionapp.ui.landing.LandingDestination
import com.example.dancognitionapp.ui.theme.DanCognitionAppTheme
import com.example.dancognitionapp.ui.landing.LandingPageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DanCognitionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LandingPageScreen() { destination ->
                        when(destination) {
                            LandingDestination.StartTrial ->
                                startActivity(AssessmentActivity.newIntent(this, false))
                            LandingDestination.Practice ->
                                startActivity(AssessmentActivity.newIntent(this, true))
                            LandingDestination.AddParticipants -> {
                                //TODO - start participants activity
                            }
                        }
                    }
                }
            }
        }
    }
}
