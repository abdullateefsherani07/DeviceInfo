package com.abdul.android.deviceinfo.home.apps

import android.graphics.drawable.Drawable

data class SimpleAppDetails(
    var packageName: String,
    var name: String,
    var icon: Drawable,
    var isSystemApp: Boolean
)