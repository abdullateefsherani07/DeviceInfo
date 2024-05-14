package com.abdul.android.deviceinfo.digitalwellbeing

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ScreenUsageViewModel(application: Application): AndroidViewModel(application) {

    private val _screenUsageState = mutableStateOf(ScreenUsageState())
    val screenUsageState: State<ScreenUsageState> = _screenUsageState

    private val repository = ScreenUsageRepository(application)
    private var screenUsage by mutableStateOf<ScreenUsage?>(null)

    init {
        loadScreenUsageDetails()
    }

    private fun loadScreenUsageDetails(){
        viewModelScope.launch {
            try {
                screenUsage = repository.getMostUsedApps()
                _screenUsageState.value = _screenUsageState.value.copy(
                    loading = false,
                    screenUsage = screenUsage,
                    error = null
                )
            } catch (e: Exception) {
                _screenUsageState.value = _screenUsageState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchScreenUsageDetails(){
        loadScreenUsageDetails()
    }

    data class ScreenUsageState(
        var loading: Boolean = true,
        var screenUsage: ScreenUsage? = null,
        var error: String? = null
    )

}