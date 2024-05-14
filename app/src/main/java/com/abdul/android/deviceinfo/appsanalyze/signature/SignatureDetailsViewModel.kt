package com.abdul.android.deviceinfo.appsanalyze.signature

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.models.StorageDetails
import kotlinx.coroutines.launch

class SignatureDetailsViewModel(application: Application): AndroidViewModel(application) {

    private val _signatureListState = mutableStateOf(SignatureListState())
    val signatureListState: State<SignatureListState> = _signatureListState

    private val repository = SignatureDetailsRepository(application)
    private var signatureList by mutableStateOf<List<StorageDetails?>?>(null)

    init {
        loadSignatureList()
    }

    private fun loadSignatureList(){
        viewModelScope.launch {
            try {
                signatureList = repository.getSignatures()
                _signatureListState.value = _signatureListState.value.copy(
                    loading = false,
                    signatureList = signatureList,
                    error = null
                )
            } catch (e: Exception) {
                _signatureListState.value = _signatureListState.value.copy(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun fetchSignatureList(){
        loadSignatureList()
    }

    data class SignatureListState(
        var loading: Boolean = true,
        var signatureList: List<StorageDetails?>? = null,
        var error: String? = null
    )

}