package com.u1tramarinet.mediasharehelper.data.repository

import java.time.LocalDateTime

data class Artist(
    val id: Int = 0,
    val name: String,
    val pinned: Boolean = false,
    val count: Int = 1,
    val firstDate: LocalDateTime,
    val latestDate: LocalDateTime,
)