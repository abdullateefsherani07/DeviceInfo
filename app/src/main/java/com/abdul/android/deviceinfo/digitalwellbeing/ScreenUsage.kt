package com.abdul.android.deviceinfo.digitalwellbeing

data class ScreenUsage(
    val totalScreenTime: Long,
    val mostUsedApps: List<AppUsage>
)

data class AppUsage(
    val packageName: String,
    val screenTime: Long
)
