package com.u1tramarinet.mediasharehelper.ui.screen

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.u1tramarinet.mediasharehelper.util.LogUtil

@Composable
fun TopScreen(
    viewModel: TopViewModel = hiltViewModel(),
    onShare: (text: String?, imageUri: Uri?) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        LogUtil.methodIn("TopScreen")
        viewModel.execute()
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(onClick = { onShare(null, null) }) {
                Text(text = "共有する")
            }
        }
    }
}