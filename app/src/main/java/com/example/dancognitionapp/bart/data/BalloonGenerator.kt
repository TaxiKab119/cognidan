package com.example.dancognitionapp.bart.data

import java.util.LinkedList
import kotlin.random.Random

private const val MIN_INFLATIONS: Int = 1
private const val MAX_INFLATIONS: Int = 20
private const val NUMBER_OF_BALLOONS: Int = 20

class BalloonGenerator {

    var balloons = LinkedList<Balloon>()
    init {
        balloons = getBalloonLinkedList()
    }

    private fun getBalloonLinkedList(): LinkedList<Balloon> {

        val balloonLinkedList = LinkedList<Balloon>()

        for (balloonNumber in 1..NUMBER_OF_BALLOONS) {
            val maxInflation = Random.nextInt(MIN_INFLATIONS, MAX_INFLATIONS)
            balloonLinkedList.add(Balloon(balloonNumber, maxInflation))
        }

        return balloonLinkedList
    }
}