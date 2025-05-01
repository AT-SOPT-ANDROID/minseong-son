package org.sopt.at.models.home


data class HomeBannerEntity(
    val id: Int,
    val imageUrl: String? = "",
    val title: String? = "",
    val subtitle: String? = ""
)
