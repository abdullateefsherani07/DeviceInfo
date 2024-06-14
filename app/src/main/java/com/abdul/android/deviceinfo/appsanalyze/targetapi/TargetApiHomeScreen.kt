package com.abdul.android.deviceinfo.appsanalyze.targetapi

import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.abdul.android.deviceinfo.ui.components.CustomPieChart
import com.abdul.android.deviceinfo.ui.components.FilledSliceDrawer
import com.abdul.android.deviceinfo.ui.components.Pies
import com.abdul.android.deviceinfo.ui.components.Slice
import com.abdul.android.deviceinfo.ui.StorageListItem
import kotlinx.coroutines.launch

@Composable
fun TargetSdkHomeScreen(viewModelStoreOwner: ViewModelStoreOwner){
    val viewModel = ViewModelProvider(viewModelStoreOwner).get(TargetApiViewModel::class.java)
    val viewState by viewModel.targetApiState
    Box(modifier = Modifier.padding(5.dp).fillMaxSize()) {
        when{
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error Occurred")
            }
            else -> {
                if (viewState.targetApiList != null){
                    val items = viewState.targetApiList ?: emptyList()
                    val chartMap = mutableMapOf<Long, Long>()
                    for (item in items){
                        chartMap[item!!.used] = item.used
                    }
                    LazyColumn {
                        item {
                            val piesList = mutableListOf<Slice>()
                            items.forEachIndexed { index, it ->
                                val colors = listOf(
                                    Color(0xFFC12652),
                                    Color(0xFF4CBB17),
                                    Color(0xFFFFA500),
                                    Color(0xFFFF5733),
                                    Color(0xFF4169E1),
                                    Color(0xFF00FFFF),
                                    Color(0xFFFF00FF),
                                    Color(0xFFA52A2A),
                                    Color(0xFFFFC0CB),
                                    Color(0xFF008080),
                                    Color(0xFF808080),
                                    Color(0xFFFA8072),
                                    Color(0xFF800080),
                                    Color(0xFF808000),
                                    Color(0xFF800000),
                                    Color(0xFF800040),
                                    Color(0xFF004080),
                                    Color(0xFF008040),
                                    Color(0xFF804000),
                                    Color(0xFF408000),
                                    Color(0xFF004040),
                                    Color(0xFF6A5ACD),
                                    Color(0xFFCD5C5C),
                                    Color(0xFF20B2AA),
                                    Color(0xFF9370DB),
                                    Color(0xFF32CD32),
                                    Color(0xFF7B68EE),
                                    Color(0xFF40E0D0),
                                    Color(0xFF6495ED),
                                    Color(0xFFDAA520)
                                )
                                if (it != null) {
                                    val slice = Slice(
                                        it.name,
                                        ((it.used.toDouble()/it.total) * 100).toFloat(),
                                        colors[index]
                                    )
                                    piesList.add(slice)
                                }
                            }
                            CustomPieChart(
                                pies = Pies(piesList),
                                modifier = Modifier.height(150.dp).padding(8.dp),
                                sliceDrawer = FilledSliceDrawer(thickness = 40f)
                            )
                        }
                        items(viewState.targetApiList!!) {
                            if (it != null) {

                                val percentage: Double = (it.used.toDouble()/it.total.toDouble()) * 100

                                val api = it.name.replace("API ", "").toInt()

                                val title: String = getAndroidVersionName(viewModel, api)
                                val subtitle = it.name
                                val value = "${it.used}"
                                val progress: Float = percentage.toFloat()
                                val modifier = when (it) {
                                    viewState.targetApiList!!.first() -> Modifier.clip(
                                        RoundedCornerShape(16.dp, 16.dp, 8.dp, 8.dp)
                                    )
                                    viewState.targetApiList!!.last() -> Modifier.clip(
                                        RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp)
                                    )
                                    else -> Modifier.clip(RoundedCornerShape(8.dp))
                                }
                                StorageListItem(
                                    modifier = modifier,
                                    title = title,
                                    subtitle = subtitle,
                                    value = value,
                                    progress = progress
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getAndroidVersionName(viewModel: AndroidViewModel, api: Int): String {
    var fullVersionName: String = ""
    val versionName = when (api) {
        1 -> "Android 1.0"
        2 -> "Android 1.1"
        3 -> "Android 1.5"
        4 -> "Android 1.6"
        5 -> "Android 2.0"
        6 -> "Android 2.0.1"
        7 -> "Android 2.1"
        8 -> "Android 2.2"
        9 -> "Android 2.3"
        10 -> "Android 2.3.3"
        11 -> "Android 3.0"
        12 -> "Android 3.1"
        13 -> "Android 3.2"
        14 -> "Android 4.0"
        15 -> "Android 4.0.3"
        16 -> "Android 4.1"
        17 -> "Android 4.2"
        18 -> "Android 4.3"
        19 -> "Android 4.4"
        20 -> "Android 4.4W"
        21 -> "Android 5.0"
        22 -> "Android 5.1"
        23 -> "Android 6"
        24 -> "Android 7"
        25 -> "Android 7.1"
        26 -> "Android 8"
        27 -> "Android 8.1"
        28 -> "Android 9"
        29 -> "Android 10"
        30 -> "Android 11"
        31 -> "Android 12"
        32 -> "Android 12L"
        33 -> "Android 13"
        34 -> "Android 14"
        35 -> "Android 15 - VANILLA ICE CREAM"
        else -> "Unknown"
    }
    viewModel.viewModelScope.launch {
        try {
            val versionCode = Build.VERSION_CODES::class.java.fields[api].name.replace("_", " ")
            fullVersionName = "$versionName - $versionCode"
        } catch (e: Exception) {
            Log.e("Target API Screen", "${e.message}")
            fullVersionName = versionName
        }
    }
    return fullVersionName
}