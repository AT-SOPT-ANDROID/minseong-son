package org.sopt.at.models.dto.request

import kotlinx.serialization.SerialName

data class SignUpRequestDto(
    @SerialName("loginId")
    val loginId: String,
    @SerialName("password")
    val password: String,
    @SerialName("nickname")
    val nickname: String,
)