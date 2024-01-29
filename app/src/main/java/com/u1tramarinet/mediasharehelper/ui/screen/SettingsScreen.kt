package com.u1tramarinet.mediasharehelper.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.u1tramarinet.mediasharehelper.R
import com.u1tramarinet.mediasharehelper.ui.MediaShareHelperLargeTopAppBar
import com.u1tramarinet.mediasharehelper.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onHistories: () -> Unit,
    onArtists: () -> Unit,
    onTags: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val value = uiState.value
    Scaffold(topBar = {
        MediaShareHelperLargeTopAppBar(
            title = stringResource(id = R.string.settings),
            onNavigateUp = onNavigateUp,
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // TODO: 各項目の下に説明文を追加する
            // TODO: 各項目の横に総数や未分類の数を表示する
            SettingItem(title = R.string.presets, count = value.countOfPresets) {

            }
            Divider()
            SettingItem(title = R.string.shared_media, count = value.countOfMedia) {
                // URLごとにサービスを分類できる
                // 分類したサービスごとにプリセットを設定できる（例えば、YouTubeであればYouTubeタグを入れておくとか）
                // このアプリを他のアプリから呼んだ時、URLに基づいてプリセットを特定する
                // デフォルトのプリセットを予め登録したりしておく
                // サービスごとに各内容の出力順やprefixとかまで指定できるようにするかは考える
            }
            SettingItem(
                title = R.string.shared_artists,
                enabled = value.isArtistsVisible,
                count = value.countOfArtists
            ) {

            }
            SettingItem(
                title = R.string.shared_tags,
                enabled = value.isTagsVisible,
                count = value.countOfTags
            ) {

            }
            Divider()
            SettingItem(title = R.string.confirm_histories, count = value.countOfHistories) {

            }
        }
    }
}

@Composable
private fun SettingItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    count: Int,
    enabled: Boolean = true,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { onItemClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(id = title))
        Spacer(modifier = Modifier.weight(1f))
        Badge(count = count)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Badge(count: Int) {
    Text(text = "$count")
}