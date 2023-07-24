package com.example.dancognitionapp.bart.data

import java.util.LinkedList
import kotlin.random.Random

const val MIN_INFLATIONS: Int = 1
const val MAX_INFLATIONS: Int = 20
const val NUMBER_OF_BALLOONS: Int = 20

class BalloonGenerator {
    fun getBalloonLinkedList(): LinkedList<Balloon> {

        val balloonLinkedList = LinkedList<Balloon>()

        for (balloonNumber in 1..NUMBER_OF_BALLOONS) {
            val maxInflation = Random.nextInt(MIN_INFLATIONS, MAX_INFLATIONS)
            balloonLinkedList.add(Balloon(balloonNumber, maxInflation))
        }

        return balloonLinkedList
    }
}