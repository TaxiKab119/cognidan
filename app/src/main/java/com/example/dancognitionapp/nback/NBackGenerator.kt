package com.example.dancognitionapp.nback

import java.util.LinkedList
import kotlin.random.Random
import kotlin.random.nextInt

private const val NUMBER_OF_PRESENTATIONS = 30
class NBackGenerator(private val testType: NBackType) {

    val items = LinkedList<NBackItem>()
    var targetFoilRatio: Double? = null
    var numberOfCharsWithLures: Int? = null
    private val cannotBeCharHashMap: HashMap<Char, MutableList<Int>> = HashMap()
    init {
        generateExcludedValuesHashMap()
        generateNBackPresentationOrder()
        checkPresentationOrderValidity()
    }
    private fun generateExcludedValuesHashMap() {
        val keys = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')
        for (key in keys) {
            cannotBeCharHashMap[key] = mutableListOf<Int>()
        }
    }

    //    private fun addToKeyList(key: Char, index: Int) {
//        cannotBeCharHashMap[key]?.add(index)
//    }
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
                // targetIndex can be as low as n + 1 so that the target-buddy can still be placed
                val targetIndex = Random.nextInt(n until NUMBER_OF_PRESENTATIONS)
                val buddyIndex = targetIndex - n

                chars[targetIndex] = targetValue
                chars[buddyIndex] = targetValue

                excludedIndices.addAll(listOf(targetIndex, buddyIndex, targetIndex + n))

                val cannotBeCharIndices = (buddyIndex - (n + 1))..(targetIndex + (n + 1))

                for (index in cannotBeCharIndices) {
                    if (index in 0 until NUMBER_OF_PRESENTATIONS &&
                        cannotBeCharHashMap[targetValue]?.contains(index) == false) {
                        cannotBeCharHashMap[targetValue]?.add(index)
                    }
                }
                println("Cannot be $targetValue indices = ${cannotBeCharHashMap[targetValue]}")
            } else {
                /**Ensure both target and buddy can be placed without ruining order*/
                var targetIndex: Int
                var buddyIndex: Int
                do {
                    val possibleIndices = (n until NUMBER_OF_PRESENTATIONS)
                        .filterNot { it in excludedIndices }
                    targetIndex = possibleIndices.random(random)
                    buddyIndex = targetIndex - n
                } while (buddyIndex in excludedIndices)

                /**Change targetValue until it is a Char that can be placed in the desired index*/
                var targetValue: Char
                // TODO - Add counter to ensure if more than 8 to restart
                do {
                    targetValue = stimuli.random(random)
                } while (cannotBeCharHashMap[targetValue]?.contains(targetIndex) == true ||
                            cannotBeCharHashMap[targetValue]?.contains(buddyIndex) == true)

                chars[targetIndex] = targetValue
                chars[buddyIndex] = targetValue
                excludedIndices.addAll(listOf(targetIndex, buddyIndex, targetIndex + n))

                val cannotBeCharIndices = (buddyIndex - (n + 1))..(targetIndex + (n + 1))

                for (index in cannotBeCharIndices) {
                    if (index in 0 until NUMBER_OF_PRESENTATIONS &&
                        cannotBeCharHashMap[targetValue]?.contains(index) == false) {
                        cannotBeCharHashMap[targetValue]?.add(index)
                    }
                }
                println("Cannot be $targetValue indices = ${cannotBeCharHashMap[targetValue]}")
                println("Working model of Chars: ${chars.contentToString()}")
            }
        }
        println("Chars before replacing Zs: ${chars.contentToString()}")
        println(cannotBeCharHashMap)

        /**Replaces placeholders with a Char that will not ruin the presentation order.*/
        val endLoop = chars.size - 1
        for (i in 0..endLoop) {
            if (chars[i] == 'Z') {
                var foil: Char
                do {
                    foil = stimuli.random(random)
                } while (cannotBeCharHashMap[foil]?.contains(i) == true)
                chars[i] = foil

                val cantBeCharIndices = listOf(i + (n - 1), i + n, i + (n + 1))

                for (index in cantBeCharIndices) {
                    if (index in 0 until NUMBER_OF_PRESENTATIONS &&
                        cannotBeCharHashMap[foil]?.contains(index) == false) {
                        cannotBeCharHashMap[foil]?.add(index)
                    }
                }
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