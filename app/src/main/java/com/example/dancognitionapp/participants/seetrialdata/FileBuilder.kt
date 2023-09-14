package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import timber.log.Timber
import java.io.File

class FileBuilder {

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
    fun buildFiles() {
        fileParams.forEach { fileParam ->
            val name = buildFileName(fileParam)
            Timber.d("TONY filename: $name")
//            val file = File(context.filesDir, name)
//            file.createNewFile()
//            if (file.exists()) {
//                files.add(file)
//            }

        }

    }
    private fun buildFileName(fileParams: CSVFileParams): String {
        val name = fileParams.participantName.replace("\t", "")
        return "${name}_${fileParams.testType}_${fileParams.diveStage}_${fileParams.day}.csv"
    }
}

data class CSVFileParams(
    val participantName: String,
    val testType: String,
    val diveStage: String,
    val day: String
)