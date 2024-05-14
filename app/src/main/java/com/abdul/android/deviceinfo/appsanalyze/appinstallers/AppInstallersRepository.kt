package com.abdul.android.deviceinfo.appsanalyze.appinstallers

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppInstallersRepository(private val context: Context) {

    suspend fun getAppInstallers(): List<StorageDetails?> {
        return withContext(Dispatchers.IO) {
            val appInstallers = mutableListOf<StorageDetails>()
            try {
                val installersMap = mutableMapOf<String, Int>()
                val packageManager = context.packageManager
                val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                for (appInfo in packages) {
                    val installerPackageName = packageManager.getInstallerPackageName(appInfo.packageName)
                    if (installerPackageName != null) {
                        val packageInfo = packageManager.getPackageInfo(installerPackageName, 0)
                        val installer = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                        installersMap[installer] = installersMap.getOrDefault(installer, 0) + 1
                    }
                }
                val totalApps = packages.size
                for((installerName, appsInstalled) in installersMap) {
                    appInstallers.add(StorageDetails(installerName, totalApps.toLong(), appsInstalled.toLong()))
                }
                appInstallers.sortedByDescending { it.used }
            } catch (e: Exception) {
                Log.e(" AppInstallersRepository", "${e.message}")
                appInstallers
            }
        }
    }

}