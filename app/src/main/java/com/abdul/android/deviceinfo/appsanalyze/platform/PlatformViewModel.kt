package com.abdul.android.deviceinfo.appsanalyze.platform

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class PlatformViewModel(application: Application): AndroidViewModel(application) {

    private val _platformListState = mutableStateOf(PlatformListState())
    val platformListState: State<PlatformListState> = _platformListState

    private val repository = LibraryRepository(application)
    private var platformList by mutableStateOf<List<StorageDetails?>?>(null)

    init {
        loadSignatureList()
    }

    private fun loadSignatureList(){
        viewModelScope.launch {
            try {
                platformList = repository.getPlatformList()
                _platformListState.value = _platformListState.value.copy(
                    loading = false,
                    platformList = platformList,
                    error = null
                )
            } catch (e: Exception) {
                _platformListState.value = _platformListState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchSignatureList(){
        loadSignatureList()
    }

    data class PlatformListState(
        var loading: Boolean = true,
        var platformList: List<StorageDetails?>? = null,
        var error: String? = null

    )

}