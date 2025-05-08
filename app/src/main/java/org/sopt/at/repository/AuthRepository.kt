package org.sopt.at.repository

import android.util.Log
import org.sopt.at.core.network.AtSoptService
import org.sopt.at.models.dto.request.SignInRequestDto
import org.sopt.at.models.dto.request.SignUpRequestDto
import org.sopt.at.models.dto.response.PostSignInResponseDto
import org.sopt.at.models.dto.response.PostSignUpResponseDto
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService : AtSoptService
) {
    suspend fun postSignIn(request: SignInRequestDto): PostSignInResponseDto? {
        return try {
            val response = apiService.postSignIn(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    body
                } else {
                    //로그용
                    null
                }
            } else {
                null
            }
        } catch (e : Exception) {
            Log.e("AuthRepository", "postSignIn: $e")
            null
        }
    }

    suspend fun postSignUp(request: SignUpRequestDto): PostSignUpResponseDto? {
        return try {
            val response = apiService.postSignUp(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.success) {
                    body
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e : Exception) {
            Log.e("AuthRepository", "postSignUp: $e")
            null
        }
    }
}