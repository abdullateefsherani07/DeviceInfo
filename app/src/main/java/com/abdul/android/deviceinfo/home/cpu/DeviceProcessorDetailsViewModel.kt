package com.abdul.android.deviceinfo.home.cpu

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.launch

class DeviceProcessorDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _deviceProcessorDetailsState = mutableStateOf(DeviceProcessorDetailsState())
    var deviceProcessorDetailsState: State<DeviceProcessorDetailsState> = _deviceProcessorDetailsState

    private val repository = DeviceProcessorDetailsRepository()
    private var deviceProcessorDetails by mutableStateOf<List<UserDeviceDetailsProperty?>?>(null)

    init {
        loadDeviceProcessorDetails()
    }

    private fun loadDeviceProcessorDetails(){
        viewModelScope.launch {
            try {
                deviceProcessorDetails = repository.getProcessorDetails()
                _deviceProcessorDetailsState.value = _deviceProcessorDetailsState.value.copy(
                    loading = false,
                    deviceProcessorDetails = deviceProcessorDetails,
                    error = null
                )
            } catch (e: Exception){
                _deviceProcessorDetailsState.value = _deviceProcessorDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchUserDeviceDetails(){
        loadDeviceProcessorDetails()
    }

    data class DeviceProcessorDetailsState(
        var loading: Boolean = true,
        var deviceProcessorDetails: List<UserDeviceDetailsProperty?>? = null,
        var error: String? = null
    )

}