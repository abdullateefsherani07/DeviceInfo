package com.abdul.android.deviceinfo

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RamViewModel(application: Application): AndroidViewModel(application) {
    
    private val _ramInfo = MutableLiveData<Pair<Long, Long>>(Pair(0L, 0L))
    val ramInfo: LiveData<Pair<Long, Long>> = _ramInfo
    
    init {
        observeRamUsage(application)
    }
    
    private fun observeRamUsage(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val ramUsage = updateRamUsage(context)
                _ramInfo.postValue(ramUsage)
                delay(1000)
            }
        }
    }
    
    private fun updateRamUsage(context: Context): Pair<Long, Long>{
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        val totalRam = memoryInfo.totalMem
        val usedRam = totalRam - memoryInfo.availMem
        return Pair(totalRam, usedRam)
    }
    
}

@Composable
fun RamInfoScreen(viewModelStoreOwner: ViewModelStoreOwner){
    val viewModel = ViewModelProvider(viewModelStoreOwner).get(RamViewModel::class.java)
    val ramInfo by viewModel.ramInfo.observeAsState()
    
    Column {
        Text(text = "Total RAM: ${ramInfo?.first}")
        Text(text = "Used RAM: ${ramInfo?.second}")
    }
}