package com.u1tramarinet.mediasharehelper.domain

import com.u1tramarinet.mediasharehelper.data.repository.Artist
import com.u1tramarinet.mediasharehelper.data.repository.ArtistRepository
import com.u1tramarinet.mediasharehelper.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveArtistsUseCase @Inject constructor(
    private val repository: ArtistRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<Artist>> = repository.getAllStream().flowOn(dispatcher)
}