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

class TagRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : TagRepository {

    private val tags: MutableList<Tag> = mutableListOf()
    private val tagsFlow: MutableSharedFlow<List<Tag>> = MutableSharedFlow(replay = 1)
    private var latestId: Int = 0

    override fun getAllStream(): Flow<List<Tag>> = tagsFlow

    override fun getStream(id: Int): Flow<Tag> = tagsFlow.map { tags ->
        tags.firstOrNull { it.id == id } ?: throw NoSuchElementException()
    }.flowOn(dispatcher)

    override suspend fun add(tag: Tag): Boolean {
        LogUtil.methodIn(TAG, "tag=$tag")
        return withContext(dispatcher) {
            if (tags.none { isSameTag(it, tag) }) {
                val newId = latestId++
                val result = tags.add(tag.copy(id = newId))
                if (result) {
                    latestId = newId
                    tagsFlow.emit(tags)
                }
                LogUtil.methodReturn(TAG, true)
            } else {
                LogUtil.methodReturn(TAG, false)
            }
        }
    }

    override suspend fun update(tag: Tag): Boolean {
        LogUtil.methodIn(TAG, "tag=$tag")
        return withContext(dispatcher) {
            val index = tags.indexOfFirst { isSameTag(it, tag) }
            if (index != -1) {
                tags[index] = tag
                tagsFlow.emit(tags)
                LogUtil.methodReturn(TAG, true)
            } else {
                LogUtil.methodReturn(TAG, false)
            }
        }
    }

    override suspend fun remove(tag: Tag): Boolean {
        LogUtil.methodIn(TAG, "tag=$tag")
        return withContext(dispatcher) {
            val result = tags.removeIf { isSameTag(it, tag) }
            if (result) {
                tagsFlow.emit(tags)
            }
            LogUtil.methodReturn(TAG, result)
        }
    }

    override suspend fun clear(): Boolean {
        LogUtil.methodIn(TAG)
        return withContext(dispatcher) {
            tags.clear()
            tagsFlow.emit(tags)
            LogUtil.methodReturn(TAG, true)
        }
    }

    private fun isSameTag(one: Tag, another: Tag): Boolean {
        return one.name == another.name
    }

    companion object {
        private val TAG = TagRepositoryImpl::class.java.simpleName
    }
}