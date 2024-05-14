package com.abdul.android.deviceinfo.home.battery

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.launch

class DeviceBatteryDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _batteryDetailsState = mutableStateOf(BatteryDetailsState())
    val batteryDetailsState: State<BatteryDetailsState> = _batteryDetailsState

    val repository = DeviceBatteryDetailsRepository(application)
    private var batteryDetails by mutableStateOf<List<UserDeviceDetailsProperty?>?>(null)

    init {
        loadBatteryDetails()
    }

    private fun loadBatteryDetails(){
        viewModelScope.launch {
            try {
                batteryDetails = repository.getBatteryDetails()
                _batteryDetailsState.value = _batteryDetailsState.value.copy(
                    loading = false,
                    batteryDetails = batteryDetails,
                    error = null
                )

            } catch (e: Exception) {
                _batteryDetailsState.value = _batteryDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchBatteryDetails(){
        loadBatteryDetails()
    }

    data class BatteryDetailsState(
        var loading: Boolean = true,
        var batteryDetails: List<UserDeviceDetailsProperty?>? = null,
        var error: String? = null
    )

}