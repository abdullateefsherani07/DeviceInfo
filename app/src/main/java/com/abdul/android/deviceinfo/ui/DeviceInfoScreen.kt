package com.abdul.android.deviceinfo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.appsanalyze.AppAnalyzeScreen
import com.abdul.android.deviceinfo.home.DeviceInfoHomeScreen
import com.abdul.android.deviceinfo.home.apps.SimpleAppDetails

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceInfoScreen(viewModelStoreOwner: ViewModelStoreOwner, navigateToAppDetails: (SimpleAppDetails) -> Unit){

    val bottomNavigationItems = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationItem("Analyze", Icons.Filled.Build, Icons.Outlined.Build),
        BottomNavigationItem("You", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
    )
    var selectedItem by remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Device Info") })
        },
        bottomBar = {
            NavigationBar {
                bottomNavigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItem) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when(selectedItem){
                0 -> {
                    DeviceInfoHomeScreen(
                        viewModelStoreOwner = viewModelStoreOwner,
                        navigateToAppDetails = navigateToAppDetails
                    )
                }
                1 -> {
                    AppAnalyzeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
            }
        }
    }

}