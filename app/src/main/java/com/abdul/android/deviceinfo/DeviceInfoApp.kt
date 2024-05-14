package com.abdul.android.deviceinfo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abdul.android.deviceinfo.home.apps.details.AppInfoScreen
import com.abdul.android.deviceinfo.home.apps.details.AppDetailsViewModel
import com.abdul.android.deviceinfo.ui.DeviceInfoScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeviceInfoApp(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner
) {
    val contactDetailsViewModel = ViewModelProvider(viewModelStoreOwner).get(AppDetailsViewModel::class.java)
    NavHost(
        navController = navController,
        startDestination = Screen.DeviceInfoScreen.route
    ){
        composable(route = Screen.DeviceInfoScreen.route){
            DeviceInfoScreen(
                viewModelStoreOwner = viewModelStoreOwner,
                navigateToAppDetails = {
                    contactDetailsViewModel.viewModelScope.launch {
                        contactDetailsViewModel.fetchAppDetails(it.packageName)
                    }
                    navController.navigate(Screen.AppDetails.route)
                }
            )
        }
        composable(route = Screen.AppDetails.route){
            AppInfoScreen(
                viewModel = contactDetailsViewModel,
                onBackPressed = {
                    navController.navigateUp()
                }
            )
        }
    }
}