package com.abdul.android.deviceinfo.home.apps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.home.apps.all.SimpleAppsListViewModel
import com.abdul.android.deviceinfo.home.apps.system.SystemAppsListViewModel
import com.abdul.android.deviceinfo.home.apps.user.UserAppsListViewModel

data class ViewModelState(
    var loading: Boolean = true,
    var apps: List<SimpleAppDetails> = emptyList(),
    var error: String? = null
)

@Composable
fun InstalledAppsHomeScreen(viewModelStoreOwner: ViewModelStoreOwner, navigateToAppDetails: (SimpleAppDetails) -> Unit){
    val viewModelState = remember {
        mutableStateOf(ViewModelState())
    }
    val filterChips = listOf("All", "User", "System")
    var filterValue by remember {
        mutableStateOf("User")
    }
    var selectedItem by remember {
        mutableStateOf(1)
    }

    when(selectedItem){
        1 -> {
            val viewModel = ViewModelProvider(viewModelStoreOwner).get(UserAppsListViewModel::class.java)
            val viewState by viewModel.userAppsListState
            viewModelState.value = viewModelState.value.copy(
                loading = viewState.loading,
                apps = viewState.userApps,
                error = viewState.error
            )
        }
        2 -> {
            val viewModel = ViewModelProvider(viewModelStoreOwner).get(SystemAppsListViewModel::class.java)
            val viewState by viewModel.systemAppsListState
            viewModelState.value = viewModelState.value.copy(
                loading = viewState.loading,
                apps = viewState.systemApps,
                error = viewState.error
            )
        }
        else -> {
            val viewModel = ViewModelProvider(viewModelStoreOwner).get(SimpleAppsListViewModel::class.java)
            val viewState by viewModel.simpleAppsListState
            viewModelState.value = viewModelState.value.copy(
                loading = viewState.loading,
                apps = viewState.simpleApps,
                error = viewState.error
            )
        }
    }

    Column() {
        LazyRow(modifier = Modifier.padding(5.dp, 0.dp)) {
            itemsIndexed(filterChips){ index, item ->
                FilterChip(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        filterValue = item
                    },
                    label = { Text(text = item) },
                    modifier = Modifier.padding(6.dp, 0.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when{
                viewModelState.value.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                viewModelState.value.error != null -> {
                    Text(text = "Error Occurred: ${viewModelState.value.error}")
                }
                else -> {
                    InstalledAppsScreen(installedApps = viewModelState.value.apps, navigateToAppDetails = navigateToAppDetails)
                }
            }
        }
    }

}