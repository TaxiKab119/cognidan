package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.dancognitionapp.BuildConfig
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileBuilder(
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository
) {

    private val files = mutableListOf<File>()
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buildFiles(
        context: Context,
        participantId: String,
        bartTrialIds: List<Int>,
        nbackTrialIds: List<Int>,
    ): List<File> {
        files.clear()
        val currentTime = getCurrentDateTimeForFilename()

        // Create BART file
        val bartName = "${participantId}_${currentTime}_BART_DATA.csv"
        val bartFile = File(context.filesDir, bartName)
        withContext(Dispatchers.IO) {
            bartFile.createNewFile()
            bartRepository.getBartTrialDataByTrialIds(bartTrialIds).collect { trials ->
                csvWriter().open(bartFile, append = false) {
                    //Header
                    writeRow(listOf("Id", "Trial_Day", "Trial_Time", "Balloon_Number", "Max_Inflations", "Number_of_Inflations", "Did_Pop"))
                    trials.forEach {
                        writeRow(
                            listOf(
                                it.userGivenParticipantId,
                                it.trialDay,
                                it.trialTime,
                                it.balloonNumber,
                                it.maxInflations,
                                it.numberOfInflations,
                                it.didPop
                            )
                        )
                    }
                }
            }
        }
        if (bartFile.exists()) {
            files.add(bartFile)
        }

        // Create NBack file
        val nBackName = "${participantId}_${currentTime}_NBACK_DATA.csv"
        val nBackFile = File(context.filesDir, nBackName)
        withContext(Dispatchers.IO) {
            nBackFile.createNewFile()
            nBackRepository.getNBackTrialsByTrialIds(nbackTrialIds).collect { trials ->
                csvWriter().open(nBackFile, append = false) {
                    //Header
                    writeRow(
                        listOf(
                            "Id", "Trial_Day", "Trial_Time", "Block_Number", "N_Value",
                            "Presentation_Number", "Reaction_Time", "Is_Target", "Categorization",
                            "Was_Correct_Action",
                        )
                    )
                    trials.forEach {
                        writeRow(
                            listOf(
                                it.userGivenParticipantId,
                                it.trialDay,
                                it.trialTime,
                                it.blockNumber,
                                it.nValue,
                                it.presentationNumber,
                                it.reactionTime,
                                it.isTarget,
                                it.categorization,
                                it.wasCorrectAction
                            )
                        )
                    }
                }
            }
        }
        if (nBackFile.exists()) {
            files.add(nBackFile)
        }

        return files
    }

    fun goToFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        return intent
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTimeForFilename(): String {
        // Get the current date and time
        val currentDateTime = LocalDateTime.now()

        // Define a date-time format suitable for filenames
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(HH_mm_ss)")

        // Format the current date and time as a string
        return currentDateTime.format(formatter)
    }
}