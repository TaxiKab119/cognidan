package com.example.dancognitionapp.nback

import timber.log.Timber
import java.util.LinkedList
import kotlin.random.Random
import kotlin.random.nextInt

private const val NUMBER_OF_PRESENTATIONS = 12
class NBackGenerator(private val testType: NBackType) {

    val items = LinkedList<NBackItem>()
    var targetFoilRatio: Double? = null
    init {
        generateNBackPresentationOrder()
        getTargetFoilRatio()
    }

    private fun generateValidChars(): CharArray {
        val stimuli = listOf('A', 'B', 'C', 'D', 'E')
        val chars = CharArray(NUMBER_OF_PRESENTATIONS) {'Z'} // Z is a placeholder (never used as a stimuli)
        val targetNumber = NUMBER_OF_PRESENTATIONS / 3
        val n = testType.value
        val random = Random(System.currentTimeMillis())

        /**This block places targets and "target buddies (key)" randomly throughout presentation order.
         * This ensures there are exactly [targetNumber] targets.
         * */
        val excludedValues = mutableListOf<Int>()
        for (i in 1..targetNumber) {
            var targetValue = stimuli.random(random)
            if (i == 1) {
                // targetIndex can be as low as n so that the target-buddy can still be placed
                val targetIndex = Random.nextInt(n until NUMBER_OF_PRESENTATIONS)
                chars[targetIndex] = targetValue
                chars[targetIndex - n] = targetValue
                excludedValues.addAll(listOf(targetIndex, targetIndex - n, targetIndex + n))
            } else {
                val possibleValues = (n until NUMBER_OF_PRESENTATIONS)
                    .filterNot { it in excludedValues }
                val targetIndex = possibleValues.random(random)

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
                excludedValues.addAll(listOf(targetIndex, targetIndex - n, targetIndex + n))
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
            if (i >= testType.value) {
                items.add(
                    NBackItem(position = i + 1, letter = chars[i], key = chars[i - testType.value])
                )
            } else {
                items.add(NBackItem(position = i + 1, letter = chars[i]))
            }
        }
    }

    private fun getTargetFoilRatio() {
        var counter = 0.0
        for (item in items) {
            if (item.isTarget()) {
                counter++
            }
        }
        targetFoilRatio = counter / items.size
    }

}

enum class NBackType(val value: Int) {
    N_1(1),
    N_2(2),
    N_3(3)
}

fun main() {
    val oneBack = NBackGenerator(NBackType.N_1)
    println("Validity: ${oneBack.targetFoilRatio}")
    println("------------------------------------")
    println("")

    val twoBack = NBackGenerator(NBackType.N_2)
    println("Validity: ${twoBack.targetFoilRatio}")
    println("------------------------------------")
    println("")

    val threeBack = NBackGenerator(NBackType.N_3)
    println("Validity: ${threeBack.targetFoilRatio}")
}