package com.abdul.android.deviceinfo.ads

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun CustomNativeAdItem(
    modifier: Modifier,
    loadedAd: NativeAd?,
    composeView: View
) {
    if (loadedAd != null) {
        Row(
            modifier = Modifier.fillMaxWidth().then(modifier),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "${loadedAd.headline}",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Badge(
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Text(
                            text = "AD",
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
//                    BadgedBox(
//                        badge = {
//                            Badge {
//                                Text(
//                                    text = "AD",
//                                    style = MaterialTheme.typography.labelSmall,
//                                    maxLines = 1,
//                                    modifier = Modifier.align(Alignment.CenterVertically)
//                                )
//                            }
//                        }
//                    ) {
//                        Text(
//                            modifier = Modifier.padding(2.dp),
//                            text = "${loadedAd.headline}",
//                            style = MaterialTheme.typography.titleMedium,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
                }

                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "${loadedAd.body}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun NativeAdView(
    ad: NativeAd?,
    adContent: @Composable (ad: NativeAd, contentView: View) -> Unit
) {
    if (ad != null) {
        val contentViewId by remember { mutableIntStateOf(View.generateViewId()) }
        val adViewId by remember { mutableIntStateOf(View.generateViewId()) }
        AndroidView(
            factory = { context ->
                val contentView = ComposeView(context).apply {
                    id = contentViewId
                }
                NativeAdView(context).apply {
                    id = adViewId
                    addView(contentView)
                }
            },
            update = { view ->
                val adView = view.findViewById<NativeAdView>(adViewId)
                val contentView = view.findViewById<ComposeView>(contentViewId)

                adView.setNativeAd(ad)
                adView.callToActionView = contentView
                contentView.setContent { adContent(ad, contentView) }
            }
        )
    }

}