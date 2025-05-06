package org.sopt.at.models.history

import org.sopt.at.core.common.CommonConstants

data class DialogState (
    val isVisible: Boolean = false,
    val type: String = CommonConstants.EMPTY_STRING
)
