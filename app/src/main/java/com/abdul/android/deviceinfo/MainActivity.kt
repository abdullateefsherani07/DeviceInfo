package com.abdul.android.deviceinfo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.abdul.android.deviceinfo.ui.theme.DeviceInfoAppTheme
import com.google.android.gms.ads.MobileAds

class MainActivity: ComponentActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 123
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            requestPermissions(arrayOf(Manifest.permission.QUERY_ALL_PACKAGES))
        }
        enableEdgeToEdge()
        MobileAds.initialize(this)
        setContent {
            val navController = rememberNavController()
            DeviceInfoAppTheme {
                DeviceInfoApp(navController = navController, viewModelStoreOwner = this)
            }
        }
    }

    private fun requestPermissions(permissions: Array<String>) {
        if (!arePermissionsGranted(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun arePermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}