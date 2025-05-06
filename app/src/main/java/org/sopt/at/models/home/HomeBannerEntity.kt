package org.sopt.at.models.home

import androidx.compose.runtime.Immutable
import org.sopt.at.core.common.CommonConstants


@Immutable
data class HomeBannerEntity(
    val id: Int,
    val imageUrl: String? = CommonConstants.EMPTY_STRING,
    val title: String? = CommonConstants.EMPTY_STRING,
    val subtitle: String? = CommonConstants.EMPTY_STRING
)
