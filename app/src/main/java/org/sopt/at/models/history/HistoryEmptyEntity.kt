package org.sopt.at.models.history

import org.sopt.at.core.common.CommonConstants

data class HistoryEmptyEntity(
    val id : Int,
    val title : String? = CommonConstants.EMPTY_STRING,
    val description : String? = CommonConstants.EMPTY_STRING
)