package com.example.dancognitionapp.bart.data

import java.util.LinkedList
import kotlin.random.Random

private const val MIN_INFLATIONS: Int = 1
private const val MAX_INFLATIONS: Int = 20
private const val NUMBER_OF_BALLOONS: Int = 20

class BalloonGenerator {

    val balloons = LinkedList<Balloon>()
    init {
        generateBalloons()
    }

    private fun generateBalloons() {
        for (balloonNumber in 1..NUMBER_OF_BALLOONS) {
            val maxInflation = Random.nextInt(MIN_INFLATIONS, MAX_INFLATIONS + 1) // REVIEW
            balloons.add(Balloon(balloonNumber, maxInflation))
        }
    }
}