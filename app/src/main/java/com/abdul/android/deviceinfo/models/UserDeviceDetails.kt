package com.abdul.android.deviceinfo.models

data class UserDeviceDetails(
    var deviceName: String,
    var model: String,
    var manufacturer: String,
    var board: String,
    var hardware: String,
    var brand: String,
    var androidDeviceId: String,
    var buildFingerprint: String
)

data class UserDeviceDetailsProperty(
    val key: String,
    var value: String
)