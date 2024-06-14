package com.abdul.android.deviceinfo.home.storage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.ads.NativeAdItem
import com.abdul.android.deviceinfo.ads.NativeAdView
import com.abdul.android.deviceinfo.ui.StorageListItem
import com.farimarwat.composenativeadmob.nativead.rememberNativeAdState
import java.text.DecimalFormat

@Composable
fun StorageDetailsScreen(viewModelStoreOwner: ViewModelStoreOwner) {
    val viewModel = ViewModelProvider(viewModelStoreOwner).get(StorageDetailsViewModel::class.java)
    val viewState by viewModel.storageDetailsState
    val storageCategoryState by viewModel.storageCategoryDetailsState


    val context = LocalContext.current
    val adState = rememberNativeAdState(
        context = context,
        adUnitId = "ca-app-pub-3940256099942544/2247696110"
    )

    Box(modifier = Modifier.fillMaxSize().padding(5.dp)){
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "${viewState.error}")
            }
            else -> {
                LazyColumn {
                    if (adState != null) {
                        item {
                            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                                NativeAdView(ad = adState) { ad, view ->
                                    NativeAdItem(modifier = Modifier.padding(5.dp), loadedAd = ad, composeView = view)
                                }
                            }
                        }
                    }
                    items(viewState.storageDetails) {
                        if (it != null) {

                            val total: Double = it.total.toDouble()/1024/1024/1024
                            val used: Double = it.used.toDouble()/1024/1024/1024

                            val formattedTotal = DecimalFormat("#.##").format(total)
                            val formattedUsed = DecimalFormat("#.##").format(used)

                            val percentage: Double = (it.used.toDouble()/it.total.toDouble()) * 100
                            val formattedPercentage =  DecimalFormat("#").format(percentage)

                            val title: String = it.name
                            val subtitle = "${formattedUsed}GB used of ${formattedTotal}GB"
                            val value = "${formattedPercentage}%"
                            val progress: Float = percentage.toFloat()

                            val modifier = when (it) {
                                viewState.storageDetails.first() -> Modifier.clip(
                                    RoundedCornerShape(16.dp, 16.dp, 4.dp, 4.dp)
                                )
                                viewState.storageDetails.last() -> Modifier.clip(
                                    RoundedCornerShape(4.dp, 4.dp, 16.dp, 16.dp)
                                )
                                else -> Modifier.clip(RoundedCornerShape(4.dp))
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