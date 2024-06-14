package com.abdul.android.deviceinfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdul.android.deviceinfo.home.apps.details.appcomponents.AppComponentViewModel
import com.abdul.android.deviceinfo.home.apps.details.AppInfoScreen
import com.abdul.android.deviceinfo.home.apps.details.AppDetailsViewModel
import com.abdul.android.deviceinfo.home.apps.details.appcomponents.AppComponentScreen
import com.abdul.android.deviceinfo.ui.DeviceInfoScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeviceInfoApp(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
    modifier: Modifier = Modifier
) {
    val appDetailsViewModel = ViewModelProvider(viewModelStoreOwner).get(AppDetailsViewModel::class.java)
    val appComponentViewModel = ViewModelProvider(viewModelStoreOwner).get(AppComponentViewModel::class.java)
    NavHost(
        navController = navController,
        startDestination = Screen.DeviceInfoScreen.route,
        modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer)
    ){
        composable(route = Screen.DeviceInfoScreen.route){
            DeviceInfoScreen(
                viewModelStoreOwner = viewModelStoreOwner,
                navigateToAppDetails = {
                    appDetailsViewModel.viewModelScope.launch {
                        appDetailsViewModel.fetchAppDetails(it.packageName)
                    }
                    navController.navigate(Screen.AppDetails.route)
                }
            )
        }
        composable(route = Screen.AppDetails.route){
            AppInfoScreen(
                viewModel = appDetailsViewModel,
                onBackPressed = {
                    navController.navigateUp()
                    appDetailsViewModel.updateState()
                },
                navigateToComponents = { title, packageName, flag ->
                    appComponentViewModel.viewModelScope.launch {
                        appComponentViewModel.loadComponents(packageName, flag)
                    }
                    navController.currentBackStackEntry?.savedStateHandle?.set("title", title)
                    navController.navigate(Screen.AppPermissionsScreen.route)
                }
            )
        }
        composable(route = Screen.AppPermissionsScreen.route){
            val title = navController.previousBackStackEntry?.savedStateHandle?.
                get<String>("title") ?: ""
            AppComponentScreen(
                viewModel = appComponentViewModel,
                componentTitle = title,
                onBackPressed = {
                    navController.navigateUp()
                    appComponentViewModel.updateState()
                }
            )
        }
    }
}