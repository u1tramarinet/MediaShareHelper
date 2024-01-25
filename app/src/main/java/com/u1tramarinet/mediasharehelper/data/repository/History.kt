package com.u1tramarinet.mediasharehelper.data.repository

import java.time.LocalDateTime

data class History(
    val id: Int = 0,
    val text: String,
    val input: Map<String, String> = mapOf(),
    val artists: List<Artist> = listOf(),
    val tags: List<Tag> = listOf(),
    val date: LocalDateTime,
)