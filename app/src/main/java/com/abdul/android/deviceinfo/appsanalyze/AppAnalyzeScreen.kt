package com.abdul.android.deviceinfo.appsanalyze

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.abdul.android.deviceinfo.appsanalyze.appinstallers.AppInstallersScreen
import com.abdul.android.deviceinfo.appsanalyze.minimumApi.MinimumApiScreen
import com.abdul.android.deviceinfo.appsanalyze.platform.PlatformsScreen
import com.abdul.android.deviceinfo.appsanalyze.signature.SignaturesScreen
import com.abdul.android.deviceinfo.appsanalyze.targetapi.TargetApiHomeScreen

data class TabItem(
    val title: String,
)

@Composable
fun AppAnalyzeScreen(viewModelStoreOwner: ViewModelStoreOwner){

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
                0 -> TargetApiHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                1 -> MinimumApiScreen(viewModelStoreOwner = viewModelStoreOwner)
                2 -> AppInstallersScreen(viewModelStoreOwner = viewModelStoreOwner)
                3 -> SignaturesScreen(viewModelStoreOwner = viewModelStoreOwner)
                else -> {
                    TargetApiHomeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
            }
        }
    }
}