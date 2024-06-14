package com.abdul.android.deviceinfo.appsanalyze.signature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.ui.components.CustomPieChart
import com.abdul.android.deviceinfo.ui.components.FilledSliceDrawer
import com.abdul.android.deviceinfo.ui.components.Pies
import com.abdul.android.deviceinfo.ui.components.Slice
import com.abdul.android.deviceinfo.ui.StorageListItem

@Composable
fun AppsSignaturesScreen(viewModelStoreOwner: ViewModelStoreOwner) {

    val viewModel = ViewModelProvider(viewModelStoreOwner).get(SignatureDetailsViewModel::class.java)
    val viewState by viewModel.signatureListState
    Box(modifier = Modifier.padding(5.dp).fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error Occurred ${viewState.error}")
            }
            else -> {
                if (viewState.signatureList != null) {
                    val items = viewState.signatureList ?: emptyList()
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
                                    Color(0xFFDAA520),
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
                                modifier = Modifier.height(100.dp).padding(8.dp),
                                sliceDrawer = FilledSliceDrawer(thickness = 40f)
                            )
                        }
                        items(viewState.signatureList!!) {
                            if (it != null) {
                                val percentage: Double = (it.used.toDouble()/it.total.toDouble()) * 100
                                val title: String = it.name
                                val value = "${it.used}"
                                val progress: Float = percentage.toFloat()
                                val modifier = when (it) {
                                    viewState.signatureList!!.first() -> Modifier.clip(
                                        RoundedCornerShape(16.dp, 16.dp, 8.dp, 8.dp)
                                    )
                                    viewState.signatureList!!.last() -> Modifier.clip(
                                        RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp)
                                    )
                                    else -> Modifier.clip(RoundedCornerShape(8.dp))
                                }
                                StorageListItem(
                                    modifier = modifier,
                                    title = title,
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