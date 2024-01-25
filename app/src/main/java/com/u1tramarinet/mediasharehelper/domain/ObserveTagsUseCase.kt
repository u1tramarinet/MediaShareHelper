package com.u1tramarinet.mediasharehelper.domain

import com.u1tramarinet.mediasharehelper.data.repository.Tag
import com.u1tramarinet.mediasharehelper.data.repository.TagRepository
import com.u1tramarinet.mediasharehelper.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObserveTagsUseCase @Inject constructor(
    private val repository: TagRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<Tag>> = repository.getAllStream().flowOn(dispatcher)
}