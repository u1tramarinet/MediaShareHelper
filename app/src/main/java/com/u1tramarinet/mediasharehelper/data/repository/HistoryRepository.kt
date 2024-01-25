package com.u1tramarinet.mediasharehelper.data.repository

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllStream(): Flow<List<History>>
    fun getStream(id: Int): Flow<History>
    suspend fun add(history: History): Boolean
    suspend fun remove(history: History): Boolean
    suspend fun clear(): Boolean
}