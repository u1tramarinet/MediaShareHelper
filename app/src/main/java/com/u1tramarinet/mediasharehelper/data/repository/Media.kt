package com.u1tramarinet.mediasharehelper.data.repository

data class Media(
    val id: Int = 0,
    val name: String,
    val associatedUrls: List<String> = listOf(),
    val presetTags: List<Tag> = listOf(),
    val isRemovable: Boolean,
)