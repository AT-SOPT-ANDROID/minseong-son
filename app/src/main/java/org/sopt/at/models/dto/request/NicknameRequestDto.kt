package org.sopt.at.models.dto.request

import kotlinx.serialization.SerialName

data class NicknameRequestDto(
    @SerialName("nickname")
    val nickname : String
)
