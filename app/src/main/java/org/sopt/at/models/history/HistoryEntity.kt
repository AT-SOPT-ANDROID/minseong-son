package org.sopt.at.models.history

data class HistoryEntity(
    val id : Int,
    val imageUrl : String? = null,
    val title : String? = null,
    val type : String? = null,
)
