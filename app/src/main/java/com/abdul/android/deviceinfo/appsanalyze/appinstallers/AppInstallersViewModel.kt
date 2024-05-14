package com.abdul.android.deviceinfo.appsanalyze.appinstallers

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class AppInstallersViewModel(application: Application): AndroidViewModel(application) {

    private val _appInstallersState = mutableStateOf(AppInstallersState())
    val appInstallersState: State<AppInstallersState> = _appInstallersState

    private val repository = AppInstallersRepository(application)
    private var appInstallers by mutableStateOf<List<StorageDetails?>?>(null)

    init {
        loadAppInstallers()
    }

    private fun loadAppInstallers(){
        viewModelScope.launch {
            try {
                appInstallers = repository.getAppInstallers()
                _appInstallersState.value = _appInstallersState.value.copy(
                    loading = false,
                    appInstallers = appInstallers,
                    error = null
                )
            } catch (e: Exception){
                _appInstallersState.value = _appInstallersState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchAppInstallers(){
        loadAppInstallers()
    }

    data class AppInstallersState(
        var loading: Boolean = true,
        var appInstallers: List<StorageDetails?>? = null,
        var error: String? = null
    )

}