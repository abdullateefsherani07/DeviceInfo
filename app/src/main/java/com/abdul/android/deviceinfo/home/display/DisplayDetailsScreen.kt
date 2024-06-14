package com.abdul.android.deviceinfo.home.display

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.abdul.android.deviceinfo.ui.components.DetailListItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayDetailsScreen(viewModelStoreOwner: ViewModelStoreOwner){

    val viewModel = ViewModelProvider(viewModelStoreOwner).get(DisplayDetailsViewModel::class.java)
    val viewState by viewModel.displayDetailsState
    Box(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        when{
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error Occurred")
            }
            else -> {
                if (viewState.displayDetails != null){
                    LazyColumn {
                        items(viewState.displayDetails!!) {
                            if (it != null) {
                                val modifier = when (it) {
                                    viewState.displayDetails!!.first() -> Modifier.clip(
                                        RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)
                                    )
                                    viewState.displayDetails!!.last() -> Modifier.clip(
                                        RoundedCornerShape(8.dp, 8.dp, 16.dp, 16.dp)
                                    )
                                    else -> Modifier.clip(RoundedCornerShape(8.dp))
                                }
                                DetailListItem(item = it, modifier = modifier)
                            }
                        }
                    }
                }

            }
        }
    }
}