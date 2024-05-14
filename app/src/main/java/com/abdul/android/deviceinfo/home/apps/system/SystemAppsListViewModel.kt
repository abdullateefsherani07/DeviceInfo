package com.abdul.android.deviceinfo.home.apps.system

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.home.apps.SimpleAppDetails
import kotlinx.coroutines.launch

class SystemAppsListViewModel(application: Application): AndroidViewModel(application) {

    private val _systemAppsListState = mutableStateOf(SystemAppsListState())
    var systemAppsListState: State<SystemAppsListState> = _systemAppsListState

    private val repository = SystemAppsListRepository(application)
    var systemApps by mutableStateOf<List<SimpleAppDetails>>(emptyList())
        private set

    init {
        loadSystemApps()
    }

    private fun loadSystemApps(){
        Log.e("ViewModel", "loadSystemApps method called")
        viewModelScope.launch {
            Log.e("ViewModel", "coroutine scope launched")
            try {
                Log.e("ViewModel", "calling getSystemInstalledApps method of repository")
                systemApps = repository.getSystemInstalledApps()
                Log.e("ViewModel", "app: ${systemApps[4].icon}")
                _systemAppsListState.value = _systemAppsListState.value.copy(
                    loading = false,
                    systemApps = systemApps,
                    error = null
                )
            } catch (e:Exception){
                _systemAppsListState.value = _systemAppsListState.value.copy(
                    loading = false,
                    error = "Error fetching apps from repository ${e.message}"
                )
            }
        }
    }

    fun fetchSystemApps(){
        loadSystemApps()
    }

    data class SystemAppsListState(
        var loading: Boolean = true,
        var systemApps: List<SimpleAppDetails> = emptyList(),
        var error: String? = null
    )

}