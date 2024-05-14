package com.abdul.android.deviceinfo.home.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.appsanalyze.targetapi.TargetApiViewModel
import com.abdul.android.deviceinfo.ui.UserDeviceDetailsItem

@Composable
fun DeviceDetailsHomeScreen(viewModelStoreOwner: ViewModelStoreOwner){
    val viewModel = ViewModelProvider(viewModelStoreOwner).get(UserDeviceDetailsViewModel::class.java)
    val targetApiViewModel = ViewModelProvider(viewModelStoreOwner).get(TargetApiViewModel::class.java)
    val viewState by viewModel.userDeviceDetailsState
    Box(modifier = Modifier.fillMaxSize()) {
        when{
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error Occurred")
            }
            else -> {
                if (viewState.userDeviceDetails != null){
                    LazyColumn {
                        item {
                            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                                viewState.userDeviceDetails!!.forEach {
                                    if (it != null) {
                                        UserDeviceDetailsItem(item = it)
                                        if (it != viewState.userDeviceDetails!!.last()){
                                            HorizontalDivider(modifier = Modifier.fillMaxWidth())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}