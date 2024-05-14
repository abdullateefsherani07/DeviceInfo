package com.abdul.android.deviceinfo.digitalwellbeing

import android.app.usage.UsageStatsManager
import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class ScreenUsageRepository(private val context: Context) {
    suspend fun getMostUsedApps(): ScreenUsage? {
        return withContext(Dispatchers.IO) {
            var screenUsage: ScreenUsage? = null
            try {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)

                val startTime = calendar.timeInMillis
                val endTime = System.currentTimeMillis()

                val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
                val appUsageMap = mutableMapOf<String, Long>()

                for(usageStats in usageStatsList) {
                    val packageName = usageStats.packageName
                    val totalScreenTime = usageStats.totalTimeInForeground
                    appUsageMap[packageName] = appUsageMap.getOrDefault(packageName, 0) + totalScreenTime
                }

                val sortedAppUsageList = appUsageMap.toList().sortedByDescending { (_, value) -> value }
                val mostUsedApps = sortedAppUsageList.take(5).map {
                    (packageName, screenTime) ->
                    AppUsage(packageName, screenTime)
                }

                val totalScreenTime = sortedAppUsageList.sumOf {
                    it.second.toInt()
                }.toLong()
                screenUsage = ScreenUsage(totalScreenTime, mostUsedApps)
                Log.e("ScreenUsageRepository", totalScreenTime.toString())
                screenUsage.mostUsedApps.forEach {
                    Log.e("ScreenUsageRepository", it.packageName)
                }
                screenUsage
            } catch (e: Exception) {
                screenUsage
            }
        }
    }
}