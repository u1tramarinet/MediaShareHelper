package com.u1tramarinet.mediasharehelper.data.repository

import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    fun getAllStream(): Flow<List<Artist>>
    fun getStream(id: Int): Flow<Artist>
    suspend fun add(artist: Artist): Boolean
    suspend fun update(artist: Artist): Boolean
    suspend fun remove(artist: Artist): Boolean
    suspend fun clear(): Boolean
}