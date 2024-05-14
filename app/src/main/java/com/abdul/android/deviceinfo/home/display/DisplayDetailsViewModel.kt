package com.abdul.android.deviceinfo.home.display

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class DisplayDetailsViewModel(application: Application): AndroidViewModel(application) {


    private val _displayDetailsState = mutableStateOf(DisplayDetailsState())
    var displayDetailsState: State<DisplayDetailsState> = _displayDetailsState

    private val repository = DisplayDetailsRepository(application)
    private var displayDetails by mutableStateOf<List<UserDeviceDetailsProperty?>?>(null)

    init {
        loadDisplayDetails()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadDisplayDetails(){
        viewModelScope.launch {
            try {
                displayDetails = repository.getDisplayDetails()
                _displayDetailsState.value = _displayDetailsState.value.copy(
                    loading = false,
                    displayDetails = displayDetails,
                    error = null
                )
            } catch (e: Exception){
                _displayDetailsState.value = _displayDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDisplayDetails(){
        loadDisplayDetails()
    }
    data class DisplayDetailsState(
        var loading: Boolean = true,
        var displayDetails: List<UserDeviceDetailsProperty?>? = null,
        var error: String? = null
    )

}