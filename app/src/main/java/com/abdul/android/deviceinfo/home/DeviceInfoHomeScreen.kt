package com.abdul.android.deviceinfo.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.abdul.android.deviceinfo.home.cpu.ProcessorDetailsHomeScreen
import com.abdul.android.deviceinfo.home.device.DeviceDetailsHomeScreen
import com.abdul.android.deviceinfo.home.apps.SimpleAppDetails
import com.abdul.android.deviceinfo.home.battery.BatteryDetailsHomeScreen
import com.abdul.android.deviceinfo.home.display.DisplayDetailsHomeScreen
import com.abdul.android.deviceinfo.home.storage.StorageDetailsHomeScreen
import com.abdul.android.deviceinfo.home.system.SystemDetailsHomeScreen

data class TabItem(
    val title: String,
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceInfoHomeScreen(viewModelStoreOwner: ViewModelStoreOwner, navigateToAppDetails: (SimpleAppDetails) -> Unit){

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

    Column() {
        ScrollableTabRow(
            selectedTabIndex = selectedTabItem,
            edgePadding = 0.dp
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
                    DeviceDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                2 -> {
                    SystemDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                3 -> {
                    ProcessorDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                4 -> {
                    BatteryDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                5 -> {
                    StorageDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                6 -> {
                    DisplayDetailsHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
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