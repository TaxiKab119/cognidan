package com.example.dancognitionapp

import com.example.dancognitionapp.assessment.nback.data.NBackGenerator
import com.example.dancognitionapp.assessment.nback.data.NBackType
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NBackGeneratorTest {
    @Test
    fun generateValidChars_forAllPossibleNValues() {
        repeat(1000) {
            val generatorOne = NBackGenerator(NBackType.N_1)
            val generatorTwo = NBackGenerator(NBackType.N_2)
            val generatorThree = NBackGenerator(NBackType.N_3)


            assertEquals(0, generatorOne.numberOfCharsWithLures)
            assertEquals(0, generatorTwo.numberOfCharsWithLures)
            assertEquals(0, generatorThree.numberOfCharsWithLures)
            assertEquals(0.33, generatorOne.targetFoilRatio!!, 0.01)
            assertEquals(0.33, generatorTwo.targetFoilRatio!!, 0.01)
            assertEquals(0.33, generatorThree.targetFoilRatio!!, 0.01)
        }
    }

    // If methods were more modularized (test would include)

    // Typical cases (like what is tested above)
    // Variable Presentation number
    // Impossible to create a list with given ratios -> should alert via exception
    // Ensure specified indices (that are no good) will fail
    // Ensure excluded indices are actually added properly
}