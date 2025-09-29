package com.example.phonebookapplication.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import android.net.Uri
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

suspend fun getDominantColorFromUrl(url: String, context: Context): Color {
    return withContext(Dispatchers.IO) {
        try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false) // needed for bitmap access
                .build()
            val result = loader.execute(request)
            val bitmap = (result.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
            bitmap?.let {
                val palette = Palette.from(it).generate()
                Color(palette.getDominantColor(android.graphics.Color.GRAY))
            } ?: Color.Gray
        } catch (_: Exception) {
            Color.Gray
        }
    }
}