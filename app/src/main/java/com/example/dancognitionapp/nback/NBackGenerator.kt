package com.example.dancognitionapp.nback

import timber.log.Timber
import java.lang.IndexOutOfBoundsException
import java.util.LinkedList
import kotlin.random.Random
import kotlin.random.nextInt

private const val NUMBER_OF_PRESENTATIONS = 12
class NBackGenerator(private val testType: NBackType) {

    val items = LinkedList<NBackItem>()
    var targetFoilRatio: Double? = null
    var numberOfCharsWithLures: Int? = null
    init {
        generateNBackPresentationOrder()
        checkPresentationOrderValidity()
    }

    private fun generateValidChars(): CharArray {
        val stimuli = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')
        val chars = CharArray(NUMBER_OF_PRESENTATIONS) {'Z'} // Z is a placeholder (never used as a stimuli)
        val targetNumber = NUMBER_OF_PRESENTATIONS / 3
        val n = testType.value
        val random = Random(System.currentTimeMillis())

        /**This block places targets and "target buddies (key)" randomly throughout presentation order.
         * This ensures there are exactly [targetNumber] targets.
         * */
        val excludedIndices = mutableListOf<Int>()
        for (i in 1..targetNumber) {
            println("Excluded Indices: $excludedIndices")
            if (i == 1) {
                val targetValue = stimuli.random(random)
                // targetIndex can be as low as n + 1 so that the target-buddy can still be placed along with pre-lure
                val targetIndex = Random.nextInt(n until NUMBER_OF_PRESENTATIONS)
                chars[targetIndex] = targetValue
                chars[targetIndex - n] = targetValue
                excludedIndices.addAll(listOf(targetIndex, targetIndex - n, targetIndex + n))
            } else {
                val possibleIndices = (n until NUMBER_OF_PRESENTATIONS)
                    .filterNot { it in excludedIndices }
                val targetIndex = possibleIndices.random(random)
                var targetValue = stimuli.random(random)

                // resolve bug of repeating a letter and creating more targets than desired
                val repeatedLetter = targetValue == chars[targetIndex] + n
                        || targetValue == chars[targetIndex - (n - 1)]
                if (repeatedLetter) {
                    val modifiedStimuli = stimuli.toMutableList()
                    modifiedStimuli.remove(targetValue)
                    targetValue = modifiedStimuli.random(random)
                }
                chars[targetIndex] = targetValue
                chars[targetIndex - n] = targetValue
                excludedIndices.addAll(listOf(targetIndex, targetIndex - n, targetIndex + n))
                println("Working model of Chars: ${chars.contentToString()}")
            }
        }
        println("Chars before replacing Zs: ${chars.contentToString()}")

        /**This code checks to see if a given index is empty. Then it chooses a letter.
         * If the letter it wants to place is adjacent (will mess up the # of targets)
         * it chooses a different letter.
         *
         * The end goal of this block is to ensure all spots are filled with stimuli in the presentation order
         * */
        val endLoop = chars.size - 1
        for (i in 0..endLoop) {
            if (chars[i] == 'Z') {
                val adjacentChars = (i - n..i + n).mapNotNull() { chars.getOrNull(it) }
                println("Adjacent Chars: $adjacentChars")
                val foilOptions = stimuli.filterNot { it in adjacentChars }
                chars[i] = foilOptions.random(random)
            }
        }
        println("Final Chars List: ${chars.contentToString()}")
        return chars
    }

    private fun generateNBackPresentationOrder() {
        val chars = generateValidChars()
        val endLoop = chars.size - 1

        for (i in 0..endLoop) {
            val plusChar = try {
                chars[i - (testType.value + 1)]
            } catch (e: IndexOutOfBoundsException) {
                null
            }
            val minusChar = try {
                if (testType.value == 1) {
                    null
                } else {
                    chars[i - (testType.value - 1)]
                }
            } catch (e: IndexOutOfBoundsException) {
                null
            }
            if (i >= testType.value) {
                items.add(
                    NBackItem(
                        position = i + 1,
                        letter = chars[i],
                        key = chars[i - testType.value],
                        plusLurePositionChar = plusChar,
                        minusLurePositionChar = minusChar
                    )
                )
            } else {
                items.add(
                    NBackItem(
                        position = i + 1,
                        letter = chars[i],
                        plusLurePositionChar = plusChar,
                        minusLurePositionChar = minusChar
                    )
                )
            }
        }
    }

    private fun checkPresentationOrderValidity() {
        var counterTargetFoil = 0.0
        var lureCounter = 0
        for (item in items) {
            if (item.isTarget()) {
                counterTargetFoil++
            }
            if (item.hasLure()) {
                println(item.position)
                lureCounter++
            }
        }
        targetFoilRatio = counterTargetFoil / items.size
        numberOfCharsWithLures = lureCounter
    }

}

enum class NBackType(val value: Int) {
    N_1(1),
    N_2(2),
    N_3(3)
}

fun main() {
    val oneBack = NBackGenerator(NBackType.N_1)
    println("Validity: ${oneBack.targetFoilRatio}, Number of Chars with lures: ${oneBack.numberOfCharsWithLures}")
    println("------------------------------------")
    println("")

    val twoBack = NBackGenerator(NBackType.N_2)
    println("Validity: ${twoBack.targetFoilRatio}, Number of Chars with lures: ${twoBack.numberOfCharsWithLures}")
    println("------------------------------------")
    println("")

    val threeBack = NBackGenerator(NBackType.N_3)
    println("Validity: ${threeBack.targetFoilRatio}, Number of Chars with lures: ${threeBack.numberOfCharsWithLures}")
}