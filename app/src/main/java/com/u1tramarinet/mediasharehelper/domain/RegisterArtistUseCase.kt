package com.u1tramarinet.mediasharehelper.domain

import com.u1tramarinet.mediasharehelper.data.repository.Artist
import com.u1tramarinet.mediasharehelper.data.repository.ArtistRepository
import com.u1tramarinet.mediasharehelper.di.DefaultDispatcher
import com.u1tramarinet.mediasharehelper.util.LogUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class RegisterArtistUseCase @Inject constructor(
    private val repository: ArtistRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(input: Input): Boolean {
        LogUtil.methodIn(TAG)
        return withContext(dispatcher) {
            LogUtil.methodReturn(TAG, repository.add(convert(input)))
        }
    }

    private fun convert(input: Input): Artist {
        val now = LocalDateTime.now()
        return Artist(
            name = input.name,
            firstDate = now,
            latestDate = now,
        )
    }

    data class Input(val name: String)

    companion object {
        private val TAG = RegisterArtistUseCase::class.java.simpleName
    }
}