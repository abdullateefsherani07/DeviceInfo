package com.abdul.android.deviceinfo.home.system

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.launch

class DeviceSystemDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _deviceSystemDetailsState = mutableStateOf(DeviceSystemDetailsState())
    var deviceSystemDetailsState: State<DeviceSystemDetailsState> = _deviceSystemDetailsState

    private val repository = DeviceSystemDetailsRepository()
    private var deviceSystemDetails by mutableStateOf<List<UserDeviceDetailsProperty?>?>(null)

    init {
        loadUserDeviceDetails()
    }

    private fun loadUserDeviceDetails(){
        viewModelScope.launch {
            try {
                deviceSystemDetails = repository.getDeviceSystemDetails()
                _deviceSystemDetailsState.value = _deviceSystemDetailsState.value.copy(
                    loading = false,
                    deviceSystemDetails = deviceSystemDetails,
                    error = null
                )
            } catch (e: Exception){
                _deviceSystemDetailsState.value = _deviceSystemDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchUserDeviceDetails(){
        loadUserDeviceDetails()
    }

    data class DeviceSystemDetailsState(
        var loading: Boolean = true,
        var deviceSystemDetails: List<UserDeviceDetailsProperty?>? = null,
        var error: String? = null
    )

}