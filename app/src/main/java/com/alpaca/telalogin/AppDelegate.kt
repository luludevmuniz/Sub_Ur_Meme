package com.alpaca.telalogin

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@HiltAndroidApp
class AppDelegate : Application() {
    val obtemUriImagem: (Bitmap) -> Uri = {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            salvarImagemAndroidQ(it) else salvarImagemAndroidLegacy(it)
    }

    private fun salvarImagemAndroidLegacy(bitmap: Bitmap): Uri {
        lateinit var uriImagem: Uri
        val appContext = applicationContext
        val filename = "temp_meme.png"
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(directory, filename)
        file.deleteOnExit()
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
        MediaScannerConnection.scanFile(
            appContext, arrayOf(file.absolutePath),
            null, null
        )
        appContext?.let {
            uriImagem = FileProvider.getUriForFile(
                it, "${appContext.packageName}.provider",
                file
            )
        }
        return uriImagem
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun salvarImagemAndroidQ(bitmap: Bitmap): Uri {
        lateinit var uriImagem: Uri
        val nomeArquivo = "temp_meme.png"
        var outputStream: OutputStream?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, nomeArquivo)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        contentResolver.also { resolver ->
            uriImagem =
                resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
            outputStream = uriImagem.let { resolver.openOutputStream(it) }
        }

        outputStream?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }

        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        uriImagem.let { contentResolver?.update(it, contentValues, null, null) }
        return uriImagem
    }
}