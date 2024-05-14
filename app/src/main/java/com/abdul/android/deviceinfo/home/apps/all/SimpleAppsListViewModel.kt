package com.abdul.android.deviceinfo.home.apps.all

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

class SimpleAppsListViewModel(application: Application): AndroidViewModel(application) {

    private val _simpleAppsListState = mutableStateOf(SimpleAppsListState())
    var simpleAppsListState: State<SimpleAppsListState> = _simpleAppsListState

    private val repository = SimpleAppsListRepository(application)
    var simpleApps by mutableStateOf<List<SimpleAppDetails>>(emptyList())
        private set

    init {
        loadSimpleApps()
    }

    private fun loadSimpleApps(){
        Log.e("ViewModel", "loadSimpleApps method called")
        viewModelScope.launch {
            Log.e("ViewModel", "coroutine scope launched")
            try {
                Log.e("ViewModel", "calling getInstalledApps method of repository")
                simpleApps = repository.getInstalledApps()
                Log.e("ViewModel", "app: ${simpleApps[4].icon}")
                _simpleAppsListState.value = _simpleAppsListState.value.copy(
                    loading = false,
                    simpleApps = simpleApps,
                    error = null
                )
            } catch (e:Exception){
                _simpleAppsListState.value = _simpleAppsListState.value.copy(
                    loading = false,
                    error = "Error fetching apps from repository ${e.message}"
                )
            }
        }
    }

    fun fetchSimpleApps(){
        loadSimpleApps()
    }

    data class SimpleAppsListState(
        var loading: Boolean = true,
        var simpleApps: List<SimpleAppDetails> = emptyList(),
        var error: String? = null
    )
}