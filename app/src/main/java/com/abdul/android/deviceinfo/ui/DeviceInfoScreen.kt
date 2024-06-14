package com.abdul.android.deviceinfo.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.RamViewModel
import com.abdul.android.deviceinfo.appsanalyze.AppsAnalyzeScreen
import com.abdul.android.deviceinfo.home.DeviceInfoHome
import com.abdul.android.deviceinfo.home.apps.SimpleAppDetails
import com.abdul.android.deviceinfo.ui.components.RamDetailsCard

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
        mutableStateOf(2)
    }
    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
        topBar = {
            TopAppBar(
                title = { Text(text = "Device Info") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
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
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(it)
        ) {
            when(selectedItem){
                0 -> {
                    DeviceInfoHome(
                        viewModelStoreOwner = viewModelStoreOwner,
                        navigateToAppDetails = navigateToAppDetails
                    )
                }
                1 -> {
                    AppsAnalyzeScreen(viewModelStoreOwner = viewModelStoreOwner)
                }
                2 -> {
                    Column(modifier = Modifier.padding(5.dp)) {
                        val viewModel = ViewModelProvider(viewModelStoreOwner).get(RamViewModel::class.java)
                        val ramInfo by viewModel.ramInfo.observeAsState()

                        if (ramInfo != null) {
                            val total = ramInfo!!.total/(1024 * 1024)
                            val used = ramInfo!!.used/(1024 * 1024)
                            val percentage = (used.toDouble()/total.toDouble()) * 100
                            Log.e("DeviceInfoScreen", "Used Ram Percentage: $percentage")
                            RamDetailsCard(ramDetails = ramInfo!!)
                        }

                    }
                }
            }
        }
    }

}