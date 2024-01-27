package com.u1tramarinet.mediasharehelper.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.u1tramarinet.mediasharehelper.util.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TopUiState(
    val text: String = "",
    val images: List<ImageInfo> = listOf(),
    val canAddImage: Boolean = true,
    val artists: List<String> = listOf(),
    val tags: List<String> = listOf(),
)

data class ImageInfo(val uri: Uri)

@HiltViewModel
class TopViewModel @Inject constructor() : ViewModel() {
    private val textFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)
    private val imagesFlow: MutableSharedFlow<List<ImageInfo>> = MutableSharedFlow(replay = 1)
    private val artistsFlow: MutableSharedFlow<List<String>> = MutableSharedFlow(replay = 1)
    private val tagsFlow: MutableSharedFlow<List<String>> = MutableSharedFlow(replay = 1)

    init {
        viewModelScope.launch {
            textFlow.emit("")
            imagesFlow.emit(listOf())
            artistsFlow.emit(listOf())
            tagsFlow.emit(listOf())
        }
    }

    val uiState: StateFlow<TopUiState> =
        combine(textFlow, imagesFlow, artistsFlow, tagsFlow) { text, images, artists, tags ->
            TopUiState(
                text = text,
                images = images,
                canAddImage = validateCanAddImage(images),
                artists = artists,
                tags = tags,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TopUiState()
        )

    fun updateText(text: String) {
        viewModelScope.launch {
            LogUtil.methodIn(TAG, "text=$text")
            textFlow.emit(text)
        }
        LogUtil.methodOut(TAG)
    }

    fun updateImages(images: List<ImageInfo>) {
        LogUtil.methodIn(TAG, "image=$images")
        viewModelScope.launch {
            imagesFlow.emit(images)
        }
        LogUtil.methodOut(TAG)
    }

    fun updateArtists(artists: List<String>) {
        LogUtil.methodIn(TAG, "artists=$artists")
        viewModelScope.launch {
            artistsFlow.emit(artists)
        }
        LogUtil.methodOut(TAG)
    }

    fun updateTags(tags: List<String>) {
        LogUtil.methodIn(TAG, "tags=$tags")
        viewModelScope.launch {
            tagsFlow.emit(tags)
        }
        LogUtil.methodOut(TAG)
    }

    private fun validateCanAddImage(images: List<ImageInfo>): Boolean {
        return (images.isEmpty())
    }

    companion object {
        private val TAG = TopViewModel::class.java.simpleName
    }
}