package com.abdul.android.deviceinfo.home.apps.details.appcomponents

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class AppComponentViewModel(application: Application): AndroidViewModel(application) {

    private val _viewState = mutableStateOf(ViewState())
    val viewState: State<ViewState> = _viewState

    private val repository = AppComponentRepository(application)
    private var components by mutableStateOf<List<SimpleComponent>>(emptyList())

    private fun fetchComponents(packageName: String, flag: Int) {
        viewModelScope.launch {
            try {
                components = repository.getComponents(packageName, flag)
                _viewState.value = _viewState.value.copy(
                    loading = false,
                    componentList = components
                )
            } catch (e: Exception) {
                _viewState.value = _viewState.value.copy(
                    loading = false,
                    error = "Error Occurred: ${e.message}"
                )
            }

        }
    }

    fun loadComponents(packageName: String, flag: Int) {
        fetchComponents(packageName, flag)
    }

    fun updateState() {
        _viewState.value = ViewState()
    }

    data class ViewState(
        val loading: Boolean = true,
        val componentList: List<SimpleComponent> = emptyList(),
        val error: String? = null
    )

}