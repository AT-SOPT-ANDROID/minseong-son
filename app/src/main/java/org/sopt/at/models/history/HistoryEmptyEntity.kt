package org.sopt.at.models.history

import androidx.compose.runtime.Immutable
import org.sopt.at.core.common.CommonConstants

@Immutable
data class HistoryEmptyEntity(
    val id : Int,
    val title : String? = CommonConstants.EMPTY_STRING,
    val description : String? = CommonConstants.EMPTY_STRING
)