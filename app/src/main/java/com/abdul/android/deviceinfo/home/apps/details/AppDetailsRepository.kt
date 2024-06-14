package com.abdul.android.deviceinfo.home.apps.details

import android.content.Context
import android.content.pm.ApplicationInfo
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
                val isSystemApp = (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
                val totalPermissions = totalComponents(packageName, PackageManager.GET_PERMISSIONS)
                val totalProviders = totalComponents(packageName, PackageManager.GET_PROVIDERS)
                val totalReceivers = totalComponents(packageName, PackageManager.GET_RECEIVERS)
                val totalServices = totalComponents(packageName, PackageManager.GET_SERVICES)
                var firstInstallDate = installDate
                var lastUpdate = lastUpdateDate

                appDetails = AppDetails(
                    packageName,
                    name,
                    icon,
                    version,
                    minSdk,
                    targetSdk,
                    installDate,
                    lastUpdateDate,
                    isSystemApp,
                    totalPermissions,
                    totalProviders,
                    totalReceivers,
                    totalServices
                )
                appDetails

            } catch (e: Exception){
                Log.e("AppDetailsRepository", e.message!!)
                null
            }
        }
    }

    private fun hasComponents(packageName: String, flag: Int): Boolean {
        val packageManager = context.packageManager
        val flagName = when(flag) {
            2 -> "Receivers"
            4 -> "Services"
            8 -> "Providers"
            else -> "Unknown"
        }
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, flag)
            val components = when (flag) {
                PackageManager.GET_PROVIDERS -> packageInfo.providers
                PackageManager.GET_RECEIVERS -> packageInfo.receivers
                PackageManager.GET_SERVICES -> packageInfo.services
                else -> arrayOf()
            }
            Log.e("hasComponents", "Package: $packageName, Component: $flagName, hasComponent: ${components != null && components.isNotEmpty()}")
            components != null && components.isNotEmpty()
        } catch (e: Exception) {
            Log.e("hasComponents", "Error Occurred: ${e.message}")
            false
        }
    }

    private fun totalComponents(packageName: String, flag: Int): Int {
        var totalComponents = 0
        val packageManager = context.packageManager
        val flagName = when(flag) {
            2 -> "Receivers"
            4 -> "Services"
            8 -> "Providers"
            4096 -> "Permissions"
            else -> "Unknown"
        }
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, flag)
            val components = when (flag) {
                PackageManager.GET_PERMISSIONS -> packageInfo.requestedPermissions
                PackageManager.GET_PROVIDERS -> packageInfo.providers
                PackageManager.GET_RECEIVERS -> packageInfo.receivers
                PackageManager.GET_SERVICES -> packageInfo.services
                else -> arrayOf()
            }
            totalComponents = components.size
            Log.e("hasComponents", "Package: $packageName, Component: $flagName, Size: ${components.size}")
            totalComponents
        } catch (e: Exception) {
            Log.e("hasComponents", "Error Occurred: ${e.message}")
            totalComponents
        }
    }

}