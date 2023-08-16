package com.example.dancognitionapp.nback

import java.util.LinkedList
import kotlin.random.Random
import kotlin.random.nextInt

private const val NUMBER_OF_PRESENTATIONS = 30
private val STIMULI = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')
class NBackGenerator(private val testType: NBackType) {

    val items = LinkedList<NBackItem>()
    var targetFoilRatio: Double? = null
    var numberOfCharsWithLures: Int? = null
    private val cannotBeCharHashMap: HashMap<Char, MutableList<Int>> = HashMap()
    init {
        generateNBackPresentationOrder()
        checkPresentationOrderValidity()
    }
    private fun populateExcludedValuesHashMap() {
        for (key in STIMULI) {
            cannotBeCharHashMap[key] = mutableListOf<Int>()
        }
    }
    private fun addToHashMap(key: Char, index: Int) {
        cannotBeCharHashMap[key]?.add(index)
    }
    /**Recursive call to generateValidChars when current charArray would create infinite loop*/
    private fun restartCharGeneration(): CharArray {
        return generateValidChars()
    }
    private fun addIndicesToHashMap(indices: List<Int>, key: Char) {
        for (index in indices) {
            if (index in 0 until NUMBER_OF_PRESENTATIONS &&
                cannotBeCharHashMap[key]?.contains(index) == false) {
                addToHashMap(key, index)
            }
        }
    }
    private fun generateValidChars(): CharArray {
        val chars = CharArray(NUMBER_OF_PRESENTATIONS) {'Z'} // Z is a placeholder (never used as a stimuli)
        val targetNumber = NUMBER_OF_PRESENTATIONS / 3
        val n = testType.value
        val random = Random(System.currentTimeMillis())

        /**Fill HashMap with Stimuli as Keys to store excluded indices for each char stimuli*/
        populateExcludedValuesHashMap()

        /**This block places targets and "target buddies (key)" randomly throughout presentation order.
         * This ensures there are exactly [targetNumber] targets.
         * */
        val excludedIndices = mutableListOf<Int>()
        for (i in 1..targetNumber) {
            println("Excluded Indices: $excludedIndices")

            var targetValue = 'A'
            var targetIndex: Int
            var buddyIndex: Int

            if (i == 1) {
                targetValue = STIMULI.random(random)
                // targetIndex can be as low as n + 1 so that the target-buddy can still be placed
                targetIndex = Random.nextInt(n until NUMBER_OF_PRESENTATIONS)
                buddyIndex = targetIndex - n
            } else {
                /**Ensure both target and buddy can be placed without ruining order*/
                var targetPlacementAttempts = 0
                do {
                    val possibleIndices = (n until NUMBER_OF_PRESENTATIONS)
                        .filterNot { it in excludedIndices }
                    targetIndex = possibleIndices.random(random)
                    buddyIndex = targetIndex - n
                    targetPlacementAttempts++
                } while (buddyIndex in excludedIndices && targetPlacementAttempts < NUMBER_OF_PRESENTATIONS)
                if (targetPlacementAttempts >= NUMBER_OF_PRESENTATIONS) {
                    return restartCharGeneration()
                }

                /**Change targetValue until it is a Char that can be placed in the desired index*/
                var targetValueAttempts = 0 // Max of 9 (stimuli.size + 1) would mean you cycled through all Chars
                var cannotPlaceCharAsTarget = true
                var cannotPlaceCharAsBuddy = true
                while ((cannotPlaceCharAsTarget || cannotPlaceCharAsBuddy) && targetValueAttempts < 9) {
                    targetValue = STIMULI.random(random)
                    cannotPlaceCharAsTarget = cannotBeCharHashMap[targetValue]?.contains(targetIndex) == true
                    cannotPlaceCharAsBuddy = cannotBeCharHashMap[targetValue]?.contains(buddyIndex) == true
                    targetValueAttempts++
                }
                if (targetValueAttempts >= 9) {
                    return restartCharGeneration()
                }
            }
            chars[targetIndex] = targetValue
            chars[buddyIndex] = targetValue
            excludedIndices.addAll(listOf(targetIndex, buddyIndex, targetIndex + n))

            val cannotBeCharRange = (buddyIndex - (n + 1))..(targetIndex + (n + 1))
            val cannotBeCharIndices = cannotBeCharRange.toList()
            addIndicesToHashMap(cannotBeCharIndices, targetValue)

            println("Cannot be $targetValue indices = ${cannotBeCharHashMap[targetValue]}")
            println("Working model of Chars: ${chars.contentToString()}")
        }
        println("Chars before replacing Zs: ${chars.contentToString()}")
        println(cannotBeCharHashMap)

        /**Replaces placeholders with a Char that will not ruin the presentation order.*/
        for (i in chars.indices) {
            if (chars[i] == 'Z') {
                var foil: Char
                var foilValueAttempts = 0
                do {
                    foil = STIMULI.random(random)
                    foilValueAttempts++
                } while (cannotBeCharHashMap[foil]?.contains(i) == true && foilValueAttempts < 9)
                if (foilValueAttempts >= 9) {
                    return restartCharGeneration()
                }

                chars[i] = foil
                val cannotBeCharIndices = listOf(i + (n - 1), i + n, i + (n + 1))
                addIndicesToHashMap(cannotBeCharIndices, foil)
            }
        }
        return chars
    }

    private fun generateNBackPresentationOrder() {
        val chars = generateValidChars()
        for (i in chars.indices) {
            val plusChar = getPlusLureChar(chars, i)
            val minusChar = getMinusLureChar(chars, i)
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
    private fun getPlusLureChar(chars: CharArray, i: Int): Char? {
        return try {
            chars[i - (testType.value + 1)]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }
    private fun getMinusLureChar(chars: CharArray, i: Int): Char? {
        return try {
            if (testType.value == 1) {
                null
            } else {
                chars[i - (testType.value - 1)]
            }
        } catch (e: IndexOutOfBoundsException) {
            null
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