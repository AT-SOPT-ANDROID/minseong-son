package org.sopt.at.models.dto.response

import kotlinx.serialization.SerialName

data class PostSignInResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data : UserId
)

data class UserId(
    @SerialName("userId")
    val userId: Long
)
