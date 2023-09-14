package com.example.dancognitionapp.participants.seetrialdata

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.dancognitionapp.BuildConfig
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
    fun buildFiles(context: Context): List<File> {
        fileParams.forEach { fileParam ->
            val name = buildFileName(fileParam)
            val file = File(context.filesDir, name)
            file.createNewFile()
            if (file.exists()) {
                files.add(file)
            }
        }
        return files
    }
    private fun buildFileName(fileParams: CSVFileParams): String {
        val name = fileParams.participantName.replace("\t", "")
        return "${name}_${fileParams.testType}_${fileParams.diveStage}_${fileParams.day}.csv"
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
    val participantName: String,
    val testType: String,
    val diveStage: String,
    val day: String
)