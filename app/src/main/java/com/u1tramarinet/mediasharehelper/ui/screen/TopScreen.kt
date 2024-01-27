package com.u1tramarinet.mediasharehelper.ui.screen

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.u1tramarinet.mediasharehelper.R
import com.u1tramarinet.mediasharehelper.ui.MediaShareHelperTopAppBar
import com.u1tramarinet.mediasharehelper.ui.viewmodel.TopViewModel

@Composable
fun TopScreen(
    viewModel: TopViewModel = hiltViewModel(),
    onSettings: () -> Unit,
    onShare: (text: String?, imageUri: Uri?) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val state = uiState.value
    LaunchedEffect(key1 = Unit) {
        viewModel.updateText("花 - 藤井 風\nhttps://..\n#NowPlaying #藤井風")
        viewModel.updateArtists(listOf("藤井 風"))
        viewModel.updateTags(listOf("NowPlaying"))
    }
    Scaffold(
        topBar = {
            MediaShareHelperTopAppBar(title = stringResource(id = R.string.app_name), actions = {
                IconButton(onClick = onSettings) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(id = R.string.settings),
                    )
                }
            })
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = state.text)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtonBar(
                title = if (state.canAddImage) R.string.add_image else R.string.change_image,
                onActionClick = {},
                onDeleteClick = {},
                isDeleteVisible = state.images.isNotEmpty(),
            )
            ActionButtonBar(
                title = R.string.add_artist,
                onActionClick = {},
                onDeleteClick = {},
                isDeleteVisible = state.artists.isNotEmpty(),
            )
            if (state.artists.isNotEmpty()) {
                Chips(items = state.artists)
            }
            ActionButtonBar(
                title = R.string.add_tag,
                onActionClick = {},
                onDeleteClick = {},
                isDeleteVisible = state.tags.isNotEmpty(),
            )
            if (state.tags.isNotEmpty()) {
                Chips(items = state.tags)
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { onShare(null, null) }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.share),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ActionButtonBar(
    icon: ImageVector = Icons.Sharp.AddCircle,
    @StringRes title: Int,
    onActionClick: () -> Unit,
    isDeleteVisible: Boolean = true,
    onDeleteClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        ActionButton(
            modifier = Modifier.weight(1f),
            icon = icon,
            title = title,
            onClick = onActionClick,
        )
        if (isDeleteVisible) {
            Spacer(modifier = Modifier.width(8.dp))
            DeleteButton(
                title = R.string.delete,
                onClick = onDeleteClick,
            )
        }
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Sharp.AddCircle,
    @StringRes title: Int,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        onClick = onClick,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = title),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = title))
        }
    }
}

@Composable
private fun DeleteButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Sharp.Delete,
    @StringRes title: Int,
    onClick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id = title),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = title))
    }
}

@Composable
private fun Chips(items: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        items.forEach {
            SuggestionChip(onClick = {}, label = { Text(text = it) })
        }
    }
}