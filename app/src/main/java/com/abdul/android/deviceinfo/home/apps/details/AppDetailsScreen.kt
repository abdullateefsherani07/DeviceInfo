package com.abdul.android.deviceinfo.home.apps.details

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(appDetails: AppDetails?, onBackPressed: () -> Unit){

    val displayedAppDetails by remember {
        mutableStateOf(appDetails)
    }

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedCallback = remember {
        object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed
            }
        }
    }

    DisposableEffect(backPressedDispatcher) {
        backPressedDispatcher!!.onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose {
            onBackPressedCallback.remove()
        }
    }

    if (displayedAppDetails != null) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "") })
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Text(text = displayedAppDetails!!.name!!)
                Text(text = displayedAppDetails!!.packageName!!)
                Text(text = displayedAppDetails!!.version!!)
                Text(text = displayedAppDetails!!.minSdk.toString()!!)
                Text(text = displayedAppDetails!!.targetSdk.toString()!!)
                Text(text = displayedAppDetails!!.installDate.toString()!!)
                Text(text = displayedAppDetails!!.lastUpdate.toString()!!)
            }
        }
    }
}