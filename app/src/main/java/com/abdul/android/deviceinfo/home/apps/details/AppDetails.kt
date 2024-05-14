package com.abdul.android.deviceinfo.home.apps.details

import android.graphics.drawable.Drawable
import java.util.Date

data class AppDetails(
    var packageName: String?,
    var name: String?,
    var icon: Drawable?,
    var version: String?,
    var minSdk: Int?,
    var targetSdk: Int?,
    var installDate: Date?,
    var lastUpdate: Date?
)
