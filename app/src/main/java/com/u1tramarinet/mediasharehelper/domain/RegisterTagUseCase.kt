package com.u1tramarinet.mediasharehelper.domain

import com.u1tramarinet.mediasharehelper.data.repository.Tag
import com.u1tramarinet.mediasharehelper.data.repository.TagRepository
import com.u1tramarinet.mediasharehelper.di.DefaultDispatcher
import com.u1tramarinet.mediasharehelper.util.LogUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class RegisterTagUseCase @Inject constructor(
    private val repository: TagRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(input: Input): Boolean {
        LogUtil.methodIn(TAG)
        return withContext(dispatcher) {
            LogUtil.methodReturn(TAG, repository.add(convert(input)))
        }
    }

    private fun convert(input: Input): Tag {
        val now = LocalDateTime.now()
        return Tag(
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