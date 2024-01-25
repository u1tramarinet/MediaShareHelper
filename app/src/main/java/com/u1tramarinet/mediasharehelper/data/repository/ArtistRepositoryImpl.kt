package com.u1tramarinet.mediasharehelper.data.repository

import com.u1tramarinet.mediasharehelper.di.DefaultDispatcher
import com.u1tramarinet.mediasharehelper.util.LogUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ArtistRepository {

    private val artists: MutableList<Artist> = mutableListOf()
    private val artistsFlow: MutableSharedFlow<List<Artist>> = MutableSharedFlow(replay = 1)
    private var latestId: Int = 0

    override fun getAllStream(): Flow<List<Artist>> = artistsFlow

    override fun getStream(id: Int): Flow<Artist> = artistsFlow.map { artists ->
        artists.firstOrNull { it.id == id } ?: throw NoSuchElementException()
    }.flowOn(dispatcher)

    override suspend fun add(artist: Artist): Boolean {
        LogUtil.methodIn(TAG, "artist=$artist")
        return withContext(dispatcher) {
            if (artists.none { isSameArtist(it, artist) }) {
                val newId = latestId++
                val result = artists.add(artist.copy(id = newId))
                if (result) {
                    latestId = newId
                    artistsFlow.emit(artists)
                }
                LogUtil.methodReturn(TAG, true)
            } else {
                LogUtil.methodReturn(TAG, false)
            }
        }
    }

    override suspend fun update(artist: Artist): Boolean {
        LogUtil.methodIn(TAG, "artist=$artist")
        return withContext(dispatcher) {
            val index = artists.indexOfFirst { isSameArtist(it, artist) }
            if (index != -1) {
                artists[index] = artist
                artistsFlow.emit(artists)
                LogUtil.methodReturn(TAG, true)
            } else {
                LogUtil.methodReturn(TAG, false)
            }
        }
    }

    override suspend fun remove(artist: Artist): Boolean {
        LogUtil.methodIn(TAG, "artist=$artist")
        return withContext(dispatcher) {
            val result = artists.removeIf { isSameArtist(it, artist) }
            if (result) {
                artistsFlow.emit(artists)
            }
            LogUtil.methodReturn(TAG, result)
        }
    }

    override suspend fun clear(): Boolean {
        LogUtil.methodIn(TAG)
        return withContext(dispatcher) {
            artists.clear()
            artistsFlow.emit(artists)
            LogUtil.methodReturn(TAG, true)
        }
    }

    private fun isSameArtist(one: Artist, another: Artist): Boolean {
        return one.name == another.name
    }

    companion object {
        private val TAG = ArtistRepositoryImpl::class.java.simpleName
    }
}