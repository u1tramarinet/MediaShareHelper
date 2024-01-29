package com.u1tramarinet.mediasharehelper.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.u1tramarinet.mediasharehelper.domain.ObserveArtistsUseCase
import com.u1tramarinet.mediasharehelper.domain.ObserveTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class SettingsUiState(
    val countOfPresets: Int = 0,
    val isPresetsVisible: Boolean = false,
    val countOfMedia: Int = 0,
    val countOfUncategorizedUrl: Int = 0,
    val isMediaVisible: Boolean = false,
    val countOfTags: Int = 0,
    val isTagsVisible: Boolean = false,
    val countOfArtists: Int = 0,
    val isArtistsVisible: Boolean = false,
    val countOfHistories: Int = 0,
    val isHistoriesVisible: Boolean = false,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeArtistsUseCase: ObserveArtistsUseCase,
    observeTagsUseCase: ObserveTagsUseCase,
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> =
        combine(observeArtistsUseCase(), observeTagsUseCase()) { artists, tags ->
            SettingsUiState(
                countOfArtists = artists.size,
                isArtistsVisible = artists.isNotEmpty(),
                countOfTags = tags.size,
                isTagsVisible = tags.isNotEmpty()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SettingsUiState()
        )
}