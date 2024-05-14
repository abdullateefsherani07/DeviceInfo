package com.abdul.android.deviceinfo

sealed class Screen(var route: String) {
    object DeviceInfoScreen: Screen("deviceinfoscreen")
    object DeviceInfoHomeScreen: Screen("deviceinfohomescreen")
    object InstalledAppsScreen: Screen("installedappsscreen")
    object AppDetails: Screen("appdetails")
}