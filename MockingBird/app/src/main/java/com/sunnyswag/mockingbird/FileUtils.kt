package com.sunnyswag.mockingbird

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    const val AUDIO_FILE = "audio_file"

    fun getOutputMediaPath(fileDir: File?): String {
        val fileName = getCurTimeStamp() + ".aac"
        return fileDir?.path + File.separator + fileName
    }

    private fun getCurTimeStamp(): String {
        return SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
    }
}