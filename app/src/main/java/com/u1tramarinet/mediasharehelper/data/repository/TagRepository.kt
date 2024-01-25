package com.u1tramarinet.mediasharehelper.data.repository

import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllStream(): Flow<List<Tag>>
    fun getStream(id: Int): Flow<Tag>
    suspend fun add(tag: Tag): Boolean
    suspend fun update(tag: Tag): Boolean
    suspend fun remove(tag: Tag): Boolean
    suspend fun clear(): Boolean
}