package com.abdul.android.deviceinfo.home.apps.details

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoScreen(
    viewModel: AppDetailsViewModel,
    onBackPressed: () -> Unit,
    navigateToComponents: (String, String, Int) -> Unit
){
    Log.e("AppScreen", "Inside AppScreen composable")
    val viewState by viewModel.appDetailsState

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedCallback = remember {
        object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    DisposableEffect(backPressedDispatcher) {
        backPressedDispatcher!!.onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose {
            onBackPressedCallback.remove()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "App Info") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(it)
            .padding(5.dp)
        ){
            when{
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                viewState.error != null -> {
                    Text(text = "Error Occurred")
                }

                else -> {
                    AppDetailsHomeScreen(
                        appDetails = viewState.appDetails,
                        navigateToComponents = navigateToComponents
                    )
                }
            }
        }
    }

}