package org.sopt.at.models.dto.response

import kotlinx.serialization.SerialName

data class GetNicknameResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: GetNicknameData
)

data class GetNicknameData(
    @SerialName("nickname")
    val nickname: String
)
