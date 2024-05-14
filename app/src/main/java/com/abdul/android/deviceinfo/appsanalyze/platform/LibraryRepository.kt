package com.abdul.android.deviceinfo.appsanalyze.platform

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LibraryRepository(private val context: Context) {

    suspend fun getPlatformList(): List<StorageDetails?>{
        return withContext(Dispatchers.IO){
            val libraryList = mutableListOf<StorageDetails?>()
            try {
                val packageManager = context.packageManager
                val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                val librariesMap = mutableMapOf<String, Int>()

                for (appInfo in installedPackages) {
                    appInfo.applicationInfo.metaData?.let { metaData ->
                        for (key in metaData.keySet()) {
                            if (isLibraryUsed(key)) {
                                val count = librariesMap.getOrDefault(key, 0)
                                librariesMap[key] = count + 1
                            }
                        }
                    }
                }

                for ((library, count) in librariesMap){
                    libraryList.add(StorageDetails(library, installedPackages.size.toLong(), count.toLong()))
                }

                libraryList.forEach {
                    if(it != null) {
                        Log.e("LibraryRepository", "Name: ${it.name}, Apps: ${it.used}, Total: ${it.total} ")
                    } else {
                        Log.e("LibraryRepository", "No library")
                    }
                }
                libraryList
            } catch (e: Exception){
                Log.e("LibraryRepository", "${e.message}")
                libraryList
            }
        }
    }
    private fun isLibraryUsed(libraryName: String): Boolean{
        val knownLibraries = listOf(
            "Firebase",
            "Google Play Billing",
            "Admob",
            "Facebook",
            "ML Kit",
            "Audience Network",
            "Analytics",
            "Android Auto",
            "ARCore"
        )
        return knownLibraries.any { libraryName.contains(it, ignoreCase = true) }
    }

}