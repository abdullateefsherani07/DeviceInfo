package com.abdul.android.deviceinfo.home.device

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.appsanalyze.targetapi.TargetApiViewModel
import com.abdul.android.deviceinfo.ui.components.DetailListItem

@Composable
fun DeviceDetailsScreen(viewModelStoreOwner: ViewModelStoreOwner){
    val viewModel = ViewModelProvider(viewModelStoreOwner).get(UserDeviceDetailsViewModel::class.java)
    val targetApiViewModel = ViewModelProvider(viewModelStoreOwner).get(TargetApiViewModel::class.java)
    val viewState by viewModel.userDeviceDetailsState
    Box(modifier = Modifier.fillMaxSize().padding(5.dp)) {
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
                        items(viewState.userDeviceDetails!!) {
                            if (it != null) {
                                val modifier = when (it) {
                                    viewState.userDeviceDetails!!.first() -> Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                                    viewState.userDeviceDetails!!.last() -> Modifier.clip(RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp))
                                    else -> Modifier.clip(RoundedCornerShape(8.dp))
                                }
                                DetailListItem(item = it, modifier = modifier)
                            }
                        }
                    }
                }
            }
        }
    }
}