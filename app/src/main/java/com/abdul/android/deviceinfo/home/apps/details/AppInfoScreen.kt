package com.abdul.android.deviceinfo.home.apps.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AppInfoScreen(
    viewModel: AppDetailsViewModel,
    onBackPressed: () -> Unit
){
    Log.e("AppScreen", "Inside AppScreen composable")
    val viewState by viewModel.appDetailsState
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.surface)
    ){
        when{
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text(text = "Error Occurred")
            }

            else -> {
                AppDetailsScreen(appDetails = viewState.appDetails) {

                }
            }
        }
    }
}