package org.sopt.at.models.home

import androidx.compose.runtime.Immutable
import org.sopt.at.core.common.CommonConstants

@Immutable
data class HomeTabEntity(
    val tab : String = CommonConstants.EMPTY_STRING
)