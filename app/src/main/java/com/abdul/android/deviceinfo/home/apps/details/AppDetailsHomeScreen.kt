package com.abdul.android.deviceinfo.home.apps.details

import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import com.abdul.android.deviceinfo.ui.components.DetailListItem
import com.abdul.android.deviceinfo.ui.components.AppDetailsHeader
import com.abdul.android.deviceinfo.ui.components.AppDetailsListItemClickable

data class AppDetailsComponent(
    var title: String,
    var size: Int,
    var flag: Int,
    var packageName: String,
    var action: (title: String, packageName: String, flag: Int) -> Unit
)

@Composable
fun AppDetailsHomeScreen(
    appDetails: AppDetails?,
    navigateToComponents: (String, String, Int) -> Unit
){

    val displayedAppDetails by remember {
        mutableStateOf(appDetails)
    }

    val appDetailsList: MutableList<UserDeviceDetailsProperty> = mutableListOf()
    if (displayedAppDetails != null) {
        val appName = UserDeviceDetailsProperty("Name", displayedAppDetails!!.name)
        appDetailsList.add(appName)
        val packageName = UserDeviceDetailsProperty("Package Name", displayedAppDetails!!.packageName)
        appDetailsList.add(packageName)
        val version = UserDeviceDetailsProperty("Version", displayedAppDetails!!.version)
        appDetailsList.add(version)
        val minSdk = UserDeviceDetailsProperty("Minimum SDK", displayedAppDetails!!.minSdk.toString())
        appDetailsList.add(minSdk)
        val targetSdk = UserDeviceDetailsProperty("Target SDK", displayedAppDetails!!.targetSdk.toString())
        appDetailsList.add(targetSdk)
        val installDate = UserDeviceDetailsProperty("Install Date", displayedAppDetails!!.installDate.toString())
        appDetailsList.add(installDate)
        val lastUpdate = UserDeviceDetailsProperty("Last Update", displayedAppDetails!!.lastUpdate.toString())
        appDetailsList.add(lastUpdate)
    }

    if (appDetails != null){
        AppDetailsScreen(
            appDetails = appDetails,
            list = appDetailsList,
            navigateToComponents = navigateToComponents
        )
    }
}

@Composable
fun AppDetailsScreen(
    appDetails: AppDetails,
    list: MutableList<UserDeviceDetailsProperty>,
    navigateToComponents: (String, String, Int) -> Unit
    ) {

    val components: MutableList<AppDetailsComponent> = mutableListOf()
    val baseComponents = listOf(
        AppDetailsComponent("Permissions", appDetails.totalPermissions, PackageManager.GET_PERMISSIONS, appDetails.packageName, navigateToComponents),
        AppDetailsComponent("Providers", appDetails.totalProviders, PackageManager.GET_PROVIDERS, appDetails.packageName, navigateToComponents),
        AppDetailsComponent("Receivers", appDetails.totalReceivers, PackageManager.GET_RECEIVERS, appDetails.packageName, navigateToComponents),
        AppDetailsComponent("Services", appDetails.totalServices, PackageManager.GET_SERVICES, appDetails.packageName, navigateToComponents)
    )
    baseComponents.forEach {
        if(it.size > 0) {
            components.add(it)
        }
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        LazyColumn {
            item {
                AppDetailsHeader(
                    app = appDetails
                )
            }
            if (components.isNotEmpty()) {
                items(components) {
                    val modifier = when (it) {
                        components.first() -> Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 8.dp, 8.dp))
                        components.last() -> Modifier.clip(RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp))
                        else -> Modifier.clip(RoundedCornerShape(8.dp))
                    }
                    AppDetailsListItemClickable(
                        component = it,
                        packageName = appDetails.packageName,
                        modifier = modifier
                    )
                }
            }
            items(list) {
                val modifier = when (it) {
                    list.first() -> Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 8.dp, 8.dp))
                    list.last() -> Modifier.clip(RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp))
                    else -> Modifier.clip(RoundedCornerShape(8.dp))
                }
                DetailListItem(item = it, modifier = modifier)
            }
        }
    }
}