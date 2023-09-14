package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.dancognitionapp.BuildConfig
import com.example.dancognitionapp.assessment.TrialDay
import com.example.dancognitionapp.assessment.TrialTime
import com.example.dancognitionapp.assessment.bart.db.BartRepository
import com.example.dancognitionapp.assessment.nback.db.NBackRepository
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class FileBuilder(
    private val coroutineScope: CoroutineScope,
    private val bartRepository: BartRepository,
    private val nBackRepository: NBackRepository
) {

    private val files = mutableListOf<File>()
    private val fileParams = mutableListOf<CSVFileParams>()

    fun addFile(csvFileParams: CSVFileParams) {
       fileParams.add(csvFileParams)
    }

    fun removeFile(csvFileParams: CSVFileParams) {
        if (fileParams.contains(csvFileParams)) {
            fileParams.remove(csvFileParams)
        }
    }
    fun buildFiles(context: Context): List<File> {
        fileParams.forEach { fileParam ->
            val name = buildFileName(fileParam)
            val file = File(context.filesDir, name)
            file.createNewFile()
            if (file.exists()) {
                files.add(file)
            }
            coroutineScope.launch(Dispatchers.IO) {
                if (fileParam.testType == TestType.BART){
                    bartRepository.loadBartTrialData(
                        participantId = fileParam.participantId,
                        trialDay = fileParam.trialDay,
                        trialTime = fileParam.trialTime
                    ).collect { entities ->
                        csvWriter().open(file, append = false) {
                            //Header
                            writeRow(listOf("Id", "Trial Day", "Trial Time", "Balloon Number", "Max Inflations", "Number of Inflations", "Did Pop"))
                            entities.forEach {
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
            }
        }
        return files
    }
    private fun buildFileName(fileParams: CSVFileParams): String {
        val name = fileParams.participantName.replace("\t", "")
        return "${name}_${fileParams.testType.name}_${fileParams.trialTime.name}_${fileParams.trialDay.name}.csv"
    }

    fun goToFileIntent(context: Context, file: File): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        val contentUri = FileProvider.getUriForFile(context, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
        val mimeType = context.contentResolver.getType(contentUri)
        intent.setDataAndType(contentUri, mimeType)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        return intent
    }
}

data class CSVFileParams(
    val participantId: String,
    val participantName: String,
    val testType: TestType,
    val trialTime: TrialTime,
    val trialDay: TrialDay
)