package com.u1tramarinet.mediasharehelper.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.u1tramarinet.mediasharehelper.R
import com.u1tramarinet.mediasharehelper.ui.MediaShareHelperLargeTopAppBar

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onHistories: () -> Unit,
    onArtists: () -> Unit,
    onTags: () -> Unit,
) {
    Scaffold(topBar = {
        MediaShareHelperLargeTopAppBar(
            title = stringResource(id = R.string.settings),
            onNavigateUp = onNavigateUp,
        )
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // TODO: 各項目の下に説明文を追加する
            // TODO: 各項目の横に総数や未分類の数を表示する
            SettingItem(title = R.string.presets) {

            }
            Divider()
            SettingItem(title = R.string.shared_media) {
                // URLごとにサービスを分類できる
                // 分類したサービスごとにプリセットを設定できる（例えば、YouTubeであればYouTubeタグを入れておくとか）
                // このアプリを他のアプリから呼んだ時、URLに基づいてプリセットを特定する
                // デフォルトのプリセットを予め登録したりしておく
                // サービスごとに各内容の出力順やprefixとかまで指定できるようにするかは考える
            }
            SettingItem(title = R.string.shared_tags) {

            }
            SettingItem(title = R.string.shared_artists) {

            }
            Divider()
            SettingItem(title = R.string.confirm_histories) {

            }
        }
    }
}

@Composable
private fun SettingItem(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onItemClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Text(text = stringResource(id = title))
    }
}