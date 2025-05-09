package org.sopt.at.models.dto.response

import kotlinx.serialization.SerialName

data class PostSignUpResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data : UserData
)

data class UserData(
    @SerialName("userId")
    val userId: Int,
    @SerialName("nickname")
    val nickname: String
)