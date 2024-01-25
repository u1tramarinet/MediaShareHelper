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

class HistoryRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : HistoryRepository {

    private val histories: MutableList<History> = mutableListOf()
    private val historiesFlow: MutableSharedFlow<List<History>> = MutableSharedFlow(replay = 1)
    private var latestId: Int = 0

    override fun getAllStream(): Flow<List<History>> = historiesFlow

    override fun getStream(id: Int): Flow<History> = historiesFlow.map { histories ->
        histories.firstOrNull { it.id == id } ?: throw NoSuchElementException()
    }.flowOn(dispatcher)

    override suspend fun add(history: History): Boolean {
        LogUtil.methodIn(TAG, "history=$history")
        return withContext(dispatcher) {
            if (histories.none { isSameHistory(it, history) }) {
                val newId = latestId++
                val result = histories.add(history.copy(id = newId))
                if (result) {
                    latestId = newId
                    historiesFlow.emit(histories)
                }
                LogUtil.methodReturn(TAG, true)
            } else {
                LogUtil.methodReturn(TAG, false)
            }
        }
    }

    override suspend fun remove(history: History): Boolean {
        LogUtil.methodIn(TAG, "history=$history")
        return withContext(dispatcher) {
            val result = histories.removeIf { isSameHistory(it, history) }
            if (result) {
                historiesFlow.emit(histories)
            }
            LogUtil.methodReturn(TAG, result)
        }
    }

    override suspend fun clear(): Boolean {
        LogUtil.methodIn(TAG)
        return withContext(dispatcher) {
            histories.clear()
            historiesFlow.emit(histories)
            LogUtil.methodReturn(TAG, true)
        }
    }

    private fun isSameHistory(one: History, another: History): Boolean {
        return one.id == another.id
    }

    companion object {
        private val TAG = HistoryRepositoryImpl::class.java.simpleName
    }
}