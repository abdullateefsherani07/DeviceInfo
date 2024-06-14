package com.abdul.android.deviceinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdul.android.deviceinfo.RamDetails

@Composable
fun RamDetailsCard(ramDetails: RamDetails){
    val total = ramDetails.total/(1024 * 1024)
    val used = ramDetails.used/(1024 * 1024)
    val available = ramDetails.available/(1024 * 1024)
    val percentage = (used.toDouble()/total.toDouble()) * 100
    val progress by remember {
        mutableStateOf(((percentage/100)*300))
    }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
        ) {
            CustomSpeedometer(value = percentage, subtitle = "RAM")
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Used: ${used}MB",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    modifier = Modifier.padding(5.dp, 0.dp),
                    text = "Free: ${available}MB",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Total: ${total}MB",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

val sampleRamDetails = RamDetails(
    total = 40000000,
    used = 30000000,
    available = 10000000
)

@Preview
@Composable
fun RamDetailsCardPreview(){
    RamDetailsCard(ramDetails = sampleRamDetails)
}