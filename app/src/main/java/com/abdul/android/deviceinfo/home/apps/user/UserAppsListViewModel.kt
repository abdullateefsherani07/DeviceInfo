package com.abdul.android.deviceinfo.home.apps.user

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

class UserAppsListViewModel(application: Application): AndroidViewModel(application) {

    private val _userAppsListState = mutableStateOf(UserAppsListState())
    var userAppsListState: State<UserAppsListState> = _userAppsListState

    private val repository = UserAppsListRepository(application)
    var userApps by mutableStateOf<List<SimpleAppDetails>>(emptyList())
        private set

    init {
        loadUserApps()
    }

    private fun loadUserApps(){
        Log.e("ViewModel", "loadUserApps method called")
        viewModelScope.launch {
            Log.e("ViewModel", "coroutine scope launched")
            try {
                Log.e("ViewModel", "calling getUserInstalledApps method of repository")
                userApps = repository.getUserInstalledApps()
                Log.e("ViewModel", "app: ${userApps[4].icon}")
                _userAppsListState.value = _userAppsListState.value.copy(
                    loading = false,
                    userApps = userApps,
                    error = null
                )
            } catch (e:Exception){
                _userAppsListState.value = _userAppsListState.value.copy(
                    loading = false,
                    error = "Error fetching apps from repository ${e.message}"
                )
            }
        }
    }

    fun fetchUserApps(){
        loadUserApps()
    }

    data class UserAppsListState(
        var loading: Boolean = true,
        var userApps: List<SimpleAppDetails> = emptyList(),
        var error: String? = null
    )

}