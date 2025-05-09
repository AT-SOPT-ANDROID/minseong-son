package org.sopt.at.models.dto.response

import kotlinx.serialization.SerialName

data class GetNicknameListResponseDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: GetNicknameListData
)

data class GetNicknameListData(
    @SerialName("nicknameList")
    val nicknameList:List<String>
)
