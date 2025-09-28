package com.example.phonebookapplication.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun String.capitalizeFirstChar(): String {
    return if (this.isNotEmpty()) this.replaceFirstChar { it.uppercaseChar() } else this
}

fun Context.copyUriToFile(uri: Uri, fileName: String): File {
    val inputStream = contentResolver.openInputStream(uri)
    val tempFile = File(cacheDir, fileName)
    inputStream.use { input ->
        FileOutputStream(tempFile).use { output ->
            input?.copyTo(output)
        }
    }
    return tempFile
}