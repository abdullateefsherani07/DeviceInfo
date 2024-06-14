package com.abdul.android.deviceinfo.appsanalyze

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.abdul.android.deviceinfo.appsanalyze.appinstallers.AppsInstallerScreen
import com.abdul.android.deviceinfo.appsanalyze.minimumApi.MinimumSdkScreen
import com.abdul.android.deviceinfo.appsanalyze.signature.AppsSignaturesScreen
import com.abdul.android.deviceinfo.appsanalyze.targetapi.TargetSdkHomeScreen

data class TabItem(
    val title: String,
)

@Composable
fun AppsAnalyzeScreen(viewModelStoreOwner: ViewModelStoreOwner){

    val tabItems = listOf(
        TabItem("Target"),
        TabItem("Minimum"),
        TabItem("Installer"),
        TabItem("Signature")
    )
    var selectedTabItem by remember {
        mutableIntStateOf(0)
    }

    Column() {
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
                0 -> TargetSdkHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                1 -> MinimumSdkScreen(viewModelStoreOwner = viewModelStoreOwner)
                2 -> AppsInstallerScreen(viewModelStoreOwner = viewModelStoreOwner)
                3 -> AppsSignaturesScreen(viewModelStoreOwner = viewModelStoreOwner)
                else -> {
                    TargetSdkHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
            }
        }
    }
}