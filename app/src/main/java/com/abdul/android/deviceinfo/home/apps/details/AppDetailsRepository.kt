package com.abdul.android.deviceinfo.home.apps.details

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class AppDetailsRepository(private var context: Context) {

    suspend fun getAppDetails(packageName: String): AppDetails? {
        return withContext(Dispatchers.IO){
            val pm: PackageManager = context.packageManager
            var appDetails: AppDetails? = null
            try {
                val packageInfo = pm.getPackageInfo(packageName, 0)
                val installDate = Date(packageInfo.firstInstallTime)
                val lastUpdateDate = Date(packageInfo.lastUpdateTime)

                val name = packageInfo.applicationInfo.loadLabel(pm).toString()
                Log.e("App Name", name)
                val packageName = packageInfo.packageName
                Log.e("App Package Name", packageName)
                val icon = packageInfo.applicationInfo.loadIcon(pm)
                val version = packageInfo.versionName
                Log.e("App Version", version)
                val minSdk = packageInfo.applicationInfo.minSdkVersion
                Log.e("App Minimum SDK", minSdk.toString())
                val targetSdk = packageInfo.applicationInfo.targetSdkVersion
                Log.e("App Target SDK", targetSdk.toString())
                var firstInstallDate = installDate
                var lastUpdate = lastUpdateDate

                appDetails = AppDetails(packageName, name, icon, version, minSdk, targetSdk, installDate, lastUpdateDate)
                appDetails

            } catch (e: Exception){
                Log.e("AppDetailsRepository", e.message!!)
                null
            }
        }
    }

}