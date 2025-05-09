package org.sopt.at.repository

import org.sopt.at.core.network.AtSoptService
import org.sopt.at.models.dto.request.NicknameRequestDto
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: AtSoptService
) {
    suspend fun getNickname(userId: Long): String {
        return try {
            val response = apiService.getNickname(userId)
            if (response.isSuccessful) {
                val nickname = response.body()?.data?.nickname
                if (!nickname.isNullOrBlank()) {
                    nickname
                } else {
                    response.body()?.message ?: "닉네임 요청 실패"
                }
            } else {
                response.body()?.message ?: "닉네임 요청 실패"
            }
        } catch (e: Exception) {
            e.message ?: "닉네임 요청 실패"
        }
    }

    suspend fun getNicknameList(keyword: String): List<String>? {
        return try {
            val response = apiService.getNicknameList(keyword)
            if (response.isSuccessful) {
                val nicknameList = response.body()?.data?.nicknameList
                nicknameList
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun patchNickname(userId: Long, request: NicknameRequestDto): String {
        return try {
            val response = apiService.patchNickname(userId, request)
            if (response.isSuccessful) {
                val updatedNickname = response.body()?.data
                if (!updatedNickname.isNullOrBlank()) {
                    updatedNickname
                } else {
                    response.body()?.message ?: "닉네임 수정 실패"
                }
            } else {
                response.body()?.message ?: "닉네임 수정 실패"
            }
        } catch (e: Exception) {
            e.message ?: "닉네임 수정 실패"
        }
    }
}
