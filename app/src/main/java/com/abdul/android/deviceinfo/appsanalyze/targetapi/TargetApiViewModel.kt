package com.abdul.android.deviceinfo.appsanalyze.targetapi

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class TargetApiViewModel(application: Application): AndroidViewModel(application) {

    private val _targetApiState = mutableStateOf(TargetApiState())
    val targetApiState: State<TargetApiState> = _targetApiState

    private val repository = TargetApiRepository(application)
    private var targetAPiList by mutableStateOf<List<StorageDetails?>?>(null)

    init {
        loadTargetApiList()
    }

    private fun loadTargetApiList(){
        viewModelScope.launch {
            try {
                targetAPiList = repository.getTargetApiLiList()
                _targetApiState.value = _targetApiState.value.copy(
                    loading = false,
                    targetApiList = targetAPiList,
                    error = null
                )
            } catch (e: Exception){
                _targetApiState.value = _targetApiState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchTargetApiList(){
        loadTargetApiList()
    }

    data class TargetApiState(
        var loading: Boolean = true,
        var targetApiList: List<StorageDetails?>? = null,
        var error: String? = null
    )

}