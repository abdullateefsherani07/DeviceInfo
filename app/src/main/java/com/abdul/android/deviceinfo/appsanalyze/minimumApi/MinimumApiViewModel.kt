package com.abdul.android.deviceinfo.appsanalyze.minimumApi

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class MinimumApiViewModel(application: Application): AndroidViewModel(application) {

    private val _minimumApiState = mutableStateOf(MinimumApiState())
    val minimumApiState: State<MinimumApiState> = _minimumApiState

    private val repository = MinimumApiRepository(application)
    private var minimumApiList by mutableStateOf<List<StorageDetails?>?>(null)

    init {
        loadMinimumApiList()
    }

    private fun loadMinimumApiList(){
        viewModelScope.launch {
            try {
                minimumApiList = repository.getMinimumApiList()
                _minimumApiState.value = _minimumApiState.value.copy(
                    loading = false,
                    minimumApiList = minimumApiList,
                    error = null
                )
            } catch (e: Exception) {
                _minimumApiState.value = _minimumApiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchMinimumApiList(){
        loadMinimumApiList()
    }

    data class MinimumApiState(
        var loading: Boolean = true,
        var minimumApiList: List<StorageDetails?>? = null,
        var error: String? = null
    )

}