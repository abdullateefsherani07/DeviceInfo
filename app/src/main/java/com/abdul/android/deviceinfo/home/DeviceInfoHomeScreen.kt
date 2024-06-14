package com.abdul.android.deviceinfo.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.home.apps.InstalledAppsHomeScreen
import com.abdul.android.deviceinfo.home.cpu.ProcessorDetailsScreen
import com.abdul.android.deviceinfo.home.device.DeviceDetailsScreen
import com.abdul.android.deviceinfo.home.apps.SimpleAppDetails
import com.abdul.android.deviceinfo.home.battery.BatteryDetailsScreen
import com.abdul.android.deviceinfo.home.display.DisplayDetailsScreen
import com.abdul.android.deviceinfo.home.storage.StorageDetailsScreen
import com.abdul.android.deviceinfo.home.system.SystemDetailsScreen

data class TabItem(
    val title: String,
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceInfoHome(viewModelStoreOwner: ViewModelStoreOwner, navigateToAppDetails: (SimpleAppDetails) -> Unit){

    val tabItems = listOf(
        TabItem("Apps"),
        TabItem("Device"),
        TabItem("System"),
        TabItem("CPU"),
        TabItem("Battery"),
        TabItem("Storage"),
        TabItem("Display"),
        TabItem("Camera")
    )
    var selectedTabItem by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabItem,
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabItem,
                    onClick = { selectedTabItem = index },
                    text = {
                        Text(text = tabItem.title)
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when(selectedTabItem){
                0 -> {
                    InstalledAppsHomeScreen(
                        viewModelStoreOwner = viewModelStoreOwner,
                        navigateToAppDetails = navigateToAppDetails
                    )
                }
                1 -> {
                    DeviceDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                2 -> {
                    SystemDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                3 -> {
                    ProcessorDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                4 -> {
                    BatteryDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                5 -> {
                    StorageDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                6 -> {
                    DisplayDetailsScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
//                7 -> RamInfoScreen(viewModelStoreOwner = viewModelStoreOwner)
                else -> {
                    InstalledAppsHomeScreen(
                        viewModelStoreOwner = viewModelStoreOwner,
                        navigateToAppDetails = navigateToAppDetails
                    )
                }
            }
        }
    }
}