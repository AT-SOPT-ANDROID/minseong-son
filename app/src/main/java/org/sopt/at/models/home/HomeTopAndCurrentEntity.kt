package org.sopt.at.models.home

import org.sopt.at.core.common.CommonConstants

data class HomeTopAndCurrentEntity(
    val id: Int,
    val imageUrl: String? = CommonConstants.EMPTY_STRING,
    val title: String = CommonConstants.EMPTY_STRING,
    val subtitle: String = CommonConstants.EMPTY_STRING,
    val rank: Int? = null,
    val isNewData : Boolean = false
)
