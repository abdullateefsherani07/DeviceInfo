package com.abdul.android.deviceinfo.home.apps.details

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _appDetailsState = mutableStateOf(AppDetailsState())
    var appDetailsState: State<AppDetailsState> = _appDetailsState

    private val repository = AppDetailsRepository(application)
    private var appDetails by mutableStateOf<AppDetails?>(AppDetails(null, null, null, null, null, null, null, null))
        private set

    private fun loadAppDetails(packageName: String){
        Log.e("ViewModel", "loadSimpleApps method called")
        viewModelScope.launch {
            Log.e("ViewModel", "coroutine scope launched")
            try {
                Log.e("ViewModel", "calling getAppDetails method of repository")
                appDetails = repository.getAppDetails(packageName)
//                Log.e("ViewModel", "app: ${simpleApps[4].icon}")
                _appDetailsState.value = _appDetailsState.value.copy(
                    loading = false,
                    appDetails = appDetails,
                    error = null
                )
            } catch (e:Exception){
                _appDetailsState.value = _appDetailsState.value.copy(
                    loading = false,
                    error = "Error fetching app details from repository ${e.message}"
                )
            }
        }
    }

    fun fetchAppDetails(packageName: String){
        loadAppDetails(packageName)
    }

    data class AppDetailsState(
        var loading: Boolean = true,
        var appDetails: AppDetails? = null,
        var error: String? = null
    )

}