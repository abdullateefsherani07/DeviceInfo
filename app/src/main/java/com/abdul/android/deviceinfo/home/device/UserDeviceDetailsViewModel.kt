package com.abdul.android.deviceinfo.home.device

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.launch

class UserDeviceDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _userDeviceDetailsState = mutableStateOf(UserDeviceDetailsState())
    var userDeviceDetailsState: State<UserDeviceDetailsState> = _userDeviceDetailsState

    private val repository = UserDeviceDetailsRepository(application)
    private var userDeviceDetails by mutableStateOf<List<UserDeviceDetailsProperty?>?>(null)

    init {
        loadUserDeviceDetails()
    }

    private fun loadUserDeviceDetails(){
        viewModelScope.launch {
            try {
                userDeviceDetails = repository.getUserDeviceDetails()
                _userDeviceDetailsState.value = _userDeviceDetailsState.value.copy(
                    loading = false,
                    userDeviceDetails = userDeviceDetails,
                    error = null
                )
            } catch (e: Exception){
                _userDeviceDetailsState.value = _userDeviceDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchUserDeviceDetails(){
        loadUserDeviceDetails()
    }

    data class UserDeviceDetailsState(
        var loading: Boolean = true,
        var userDeviceDetails: List<UserDeviceDetailsProperty?>? = null,
        var error: String? = null
    )

}