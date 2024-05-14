package com.abdul.android.deviceinfo.home.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.abdul.android.deviceinfo.ui.InstalledApp

@Composable
fun InstalledAppsScreen(installedApps: List<SimpleAppDetails>, navigateToAppDetails: (SimpleAppDetails) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        items(installedApps.sortedBy{it.name}){
            InstalledApp(installedApp = it, navigateToAppDetails = navigateToAppDetails)
        }
    }
}

