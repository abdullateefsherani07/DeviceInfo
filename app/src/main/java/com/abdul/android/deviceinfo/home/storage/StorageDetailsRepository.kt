package com.abdul.android.deviceinfo.home.storage

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception

data class StorageCategory(
    var name: String,
    var size: Long
)


class StorageDetailsRepository(private val context: Context) {

    suspend fun getStorageDetails(): List<StorageDetails?> {
        return withContext(Dispatchers.IO) {
            val storageDetails = mutableListOf<StorageDetails?>()
            try {
                val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val memoryInfo = ActivityManager.MemoryInfo()
                activityManager.getMemoryInfo(memoryInfo)

                val totalRam = memoryInfo.totalMem
                val usedRam = totalRam - memoryInfo.availMem

                val totalRamBytes = Runtime.getRuntime().totalMemory()
                val usedRamBytes = totalRamBytes - Runtime.getRuntime().freeMemory()

                val ram = StorageDetails("Random Access Memory", totalRam, usedRam)
                storageDetails.add(ram)

                val systemStats = StatFs("/system")
                val totalSystemStorageBytes = systemStats.blockCountLong * systemStats.blockSizeLong
                val usedSystemStorageBytes = totalSystemStorageBytes - (systemStats.availableBlocksLong * systemStats.blockSizeLong)

                val systemStorage = StorageDetails("System Storage", totalSystemStorageBytes, usedSystemStorageBytes)
                storageDetails.add(systemStorage)

                val internalStats = StatFs("/data")
                val totalInternalStorageBytes = internalStats.blockCountLong * internalStats.blockSizeLong
                val usedInternalStorageBytes = totalInternalStorageBytes - (internalStats.availableBlocksLong * internalStats.blockSizeLong)

                val internalStorage = StorageDetails("Internal Storage", totalInternalStorageBytes, usedInternalStorageBytes)
                storageDetails.add(internalStorage)
                storageDetails.forEach {
                    if (it != null){
                        Log.e("StorageDetailsRepository", "Name: ${it.name}, Total Bytes: ${it.total}, Used Bytes: ${it.used}")
                    }
                }
                storageDetails
            } catch (e: Exception) {
                Log.e("StorageDetailsRepository", "${e.message}")
                storageDetails
            }
        }
    }

    suspend fun getStorageCategoryDetails(): List<StorageCategory?> {

        Log.e("Storage Category", "getStorageCategoryDetails() method called")
        return withContext(Dispatchers.IO) {
            val storageCategoryDetails = mutableListOf<StorageCategory>()
            try {

                val rootDir = Environment.getExternalStorageDirectory()
                calculateStorage(rootDir, storageCategoryDetails)

                storageCategoryDetails.forEach {
                    Log.e("Storage Category", "Name: ${it.name}, Size: ${it.size}")
                }
                storageCategoryDetails
            } catch (e: Exception) {
                Log.e("StorageCategoryDetails", "${e.message}")
                storageCategoryDetails
            }
        }
    }

    private fun calculateStorage(file: File, storageCategoryDetails: MutableList<StorageCategory>) {
        Log.e("Storage Category", "calculateStorage() method called")
        if (file.isDirectory) {
            file.listFiles()?.forEach { child ->
                calculateStorage(child, storageCategoryDetails)
            }
        } else {
            val fileSize = file.length()
            val category = getFileCategory(file)
            val categoryDetail = storageCategoryDetails.find {
                it.name == category
            }
            if (categoryDetail != null) {
                categoryDetail.size += fileSize
            } else {
                storageCategoryDetails.add(StorageCategory(category, fileSize))
            }
        }
    }

    private fun getFileCategory(file: File): String {
        Log.e("Storage Category", "getFileCategory() method called")
        return when {
            isAppFile(file) -> "Apps"
            isImageFile(file) -> "Images"
            isVideoFile(file) -> "Videos"
            isAudioFile(file) -> "Audios"
            isDocumentFile(file) -> "Documents"
            else -> "Others"
        }
    }

    private fun isAppFile(file: File): Boolean {
        return file.extension == "apk"
    }

    private fun isImageFile(file: File): Boolean {
        val imageExtensions = arrayOf("jpg", "jpeg", "png", "gif", "bmp")
        return imageExtensions.contains(file.extension.lowercase())
    }

    private fun isVideoFile(file: File): Boolean {
        val videoExtensions = arrayOf("mp4", "avi", "mkv", "mov", "wmv")
        return videoExtensions.contains(file.extension.lowercase())
    }

    private fun isAudioFile(file: File): Boolean {
        val audioExtensions = arrayOf("mp3", "wav", "aac", "ogg", "wma")
        return audioExtensions.contains(file.extension.lowercase())
    }

    private fun isDocumentFile(file: File): Boolean {
        val documentExtensions = arrayOf("pdf", "doc", "docx", "txt", "ppt", "pptx", "xls", "xlsx")
        return documentExtensions.contains(file.extension.lowercase())
    }
}