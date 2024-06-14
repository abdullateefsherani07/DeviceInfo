package com.abdul.android.deviceinfo.home.storage

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class StorageDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _storageDetailsState = mutableStateOf(StorageDetailsState())
    val storageDetailsState: State<StorageDetailsState> = _storageDetailsState

    private val _storageCategoryDetailsState = mutableStateOf(StorageCategoryDetailsState())
    val storageCategoryDetailsState: State<StorageCategoryDetailsState> = _storageCategoryDetailsState

    private val repository = StorageDetailsRepository(application)

    private var storageDetails by mutableStateOf<List<StorageDetails?>>(emptyList())
    private var storageCategoryDetails by mutableStateOf<List<StorageCategory?>>(emptyList())

    init {
        loadStorageDetails()
//        loadStorageCategoryDetails()
    }

    private fun loadStorageDetails(){
        viewModelScope.launch {
            try {
                storageDetails = repository.getStorageDetails()
                storageCategoryDetails = repository.getStorageCategoryDetails()
                _storageDetailsState.value = _storageDetailsState.value.copy(
                    loading = false,
                    storageDetails = storageDetails,
                    error = null
                )
            } catch (e: Exception) {
                _storageDetailsState.value = _storageDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }
    private fun loadStorageCategoryDetails(){
        viewModelScope.launch {
            try {
                storageCategoryDetails = repository.getStorageCategoryDetails()
                _storageCategoryDetailsState.value = _storageCategoryDetailsState.value.copy(
                    loading = false,
                    storageDetails = storageCategoryDetails,
                    error = null
                )
            } catch (e: Exception) {
                _storageCategoryDetailsState.value = _storageCategoryDetailsState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchStorageDetails(){
        loadStorageDetails()
    }

    data class StorageDetailsState(
        var loading: Boolean = true,
        var storageDetails: List<StorageDetails?> = emptyList(),
        var error: String? = null
    )

    data class StorageCategoryDetailsState(
        var loading: Boolean = true,
        var storageDetails: List<StorageCategory?> = emptyList(),
        var error: String? = null
    )

}