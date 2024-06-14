package com.abdul.android.deviceinfo.home.apps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.abdul.android.deviceinfo.ui.InstalledApp

@Composable
fun InstalledAppsScreen(installedApps: List<SimpleAppDetails>, navigateToAppDetails: (SimpleAppDetails) -> Unit){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        items(installedApps.sortedBy{it.name}){ item ->
            val modifier = when (item) {
                installedApps.sortedBy{it.name}.first() -> Modifier.clip(
                    RoundedCornerShape(16.dp, 16.dp, 4.dp, 4.dp)
                )
                installedApps.sortedBy{it.name}.last() -> Modifier.clip(
                    RoundedCornerShape(4.dp, 4.dp, 16.dp, 16.dp)
                )
                else -> Modifier.clip(RoundedCornerShape(4.dp))
            }
            InstalledApp(installedApp = item, navigateToAppDetails = navigateToAppDetails, modifier = modifier)
        }
    }
}

