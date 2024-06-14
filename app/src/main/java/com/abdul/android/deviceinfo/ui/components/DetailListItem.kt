package com.abdul.android.deviceinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty

@Composable
fun DetailListItem(item: UserDeviceDetailsProperty, modifier: Modifier = Modifier){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 2.dp)
            .then(modifier)
            .background(color = MaterialTheme.colorScheme.background)
            .padding(5.dp)
    ) {
        Text(
            modifier = Modifier.padding(2.dp),
            text = item.key,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier.padding(2.dp),
            text = item.value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}