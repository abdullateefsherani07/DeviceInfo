package com.abdul.android.deviceinfo.ui.components

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.abdul.android.deviceinfo.R
import com.abdul.android.deviceinfo.home.apps.details.AppDetails

@Composable
fun AppDetailsHeader(app: AppDetails) {
    val logo = app.icon.toBitmap(width = 150, height = 150).asImageBitmap()
    val context = LocalContext.current
    val canOpenApp = isAppOpenable(context, app.packageName)
    val canUninstallApp = app.isSystemApp
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(top = 24.dp)
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(27)
                    )
                    .clip(RoundedCornerShape(27)),
                bitmap = logo,
                contentDescription = null
            )
            Text(
                text = app.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(5.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(27))
                    .background(MaterialTheme.colorScheme.background)
                    .alpha(if(canOpenApp) 1f else 0.4f)
                    .clickable {  }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_app_info_open),
                    contentDescription = "Open ${app.name}",
                    modifier = Modifier
                        .alpha(if(canOpenApp) 1f else 0.4f)
                        .padding(5.dp)
                )
                Text(
                    text = "Open",
                    modifier = Modifier
                        .alpha(if(canOpenApp) 1f else 0.4f)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
//                    .weight(1f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(27))
                    .background(MaterialTheme.colorScheme.background)
                    .clickable {  }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_app_info_google_play),
                    contentDescription = "Open ${app.name} in Google Play Store",
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(text = "Google Play")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(27))
                    .background(MaterialTheme.colorScheme.background)
                    .alpha(if(canUninstallApp) 1f else 0.4f)
                    .clickable {  }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_app_info_uninstall),
                    contentDescription = "Uninstall ${app.name}",
                    modifier = Modifier
                        .alpha(if(canUninstallApp) 1f else 0.4f)
                        .padding(5.dp)
                )
                Text(
                    text = "Uninstall",
                    modifier = Modifier
                        .alpha(if(canUninstallApp) 1f else 0.4f)
                )
            }
        }
    }
}

private fun isAppOpenable(context: Context, packageName: String): Boolean {
    val packageManager = context.packageManager
    return try {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        launchIntent != null
    } catch(e: PackageManager.NameNotFoundException){
        false
    }
}