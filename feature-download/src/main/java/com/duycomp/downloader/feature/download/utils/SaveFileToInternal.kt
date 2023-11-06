package com.duycomp.downloader.feature.download.utils

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.DataInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import com.duycomp.downloader.core.common.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun saveFileToExternal(
    url: String,
    mimeType: String,
    directory: String,
    fileName: String,
    context: Context
): Result<String> = withContext(Dispatchers.IO)
{
    return@withContext if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        writeToStorageApi29(url, mimeType, directory, fileName, context)
    else
        writeToStorageApi28(url, mimeType, directory, fileName, context)
}

suspend fun writeToStorageApi29(
    url: String,
    mimeType: String,
    directory: String,
    fileName: String,
    context: Context
): Result<String> {
    try {
        val resolver = context.contentResolver
        val values = ContentValues()
        // save to a folder
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_DOWNLOADS + "/" + directory
        )
        val uri = resolver?.insert(MediaStore.Files.getContentUri("external"), values)

        val outputStream = resolver.openOutputStream(uri!!)
        // Or alternatively call other methods of ContentResolver, e.g. .openFile

        val inputStream = URL(url).openStream()
        val dataInputStream = DataInputStream(inputStream)
        val buffer = ByteArray(1024)

        while (true) {
            val it = dataInputStream.read(buffer)
            if (it > 0) {
                outputStream?.write(buffer, 0, it)
            } else {
                dataInputStream.close()
                outputStream?.close()

                return Result.Success(uri.toString())
            }
        }
    } catch (e: Exception) {
        return Result.Error(e)
    }
}

suspend fun writeToStorageApi28(
    url: String,
    mimeType: String,
    directory: String,
    fileName: String,
    context: Context
): Result<String> {
    try {


        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadDir, directory)
        if (!file.exists()) file.mkdirs()

        val filePath = file.absolutePath + "/${fileName}.${mimeType.substringAfterLast("/")}"

        val fos = FileOutputStream(filePath)

        val `is` = URL(url).openStream()
        val dis = DataInputStream(`is`)
        val buffer = ByteArray(1024)

        while (true) {
            val it = dis.read(buffer)
            if (it > 0) {
                fos.write(buffer, 0, it)
            } else {
                dis.close()
                fos.close()
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(filePath),
                    arrayOf(mimeType),
                    null
                )

                return Result.Success(filePath)
            }
        }
    } catch (e: Exception) {
        return Result.Error(e)
    }
}

