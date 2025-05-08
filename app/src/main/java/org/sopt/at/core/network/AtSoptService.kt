package org.sopt.at.core.network

import org.sopt.at.models.dto.request.NicknameRequestDto
import org.sopt.at.models.dto.request.SignInRequestDto
import org.sopt.at.models.dto.request.SignUpRequestDto
import org.sopt.at.models.dto.response.GetNicknameListResponseDto
import org.sopt.at.models.dto.response.GetNicknameResponseDto
import org.sopt.at.models.dto.response.PatchNicknameResponseDto
import org.sopt.at.models.dto.response.PostSignInResponseDto
import org.sopt.at.models.dto.response.PostSignUpResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Header
import retrofit2.http.PATCH

interface AtSoptService {
    @POST("/api/v1/auth/signup")
    suspend fun postSignUp(
        @Body request: SignUpRequestDto
    ): Response<PostSignUpResponseDto>

    @POST("/api/v1/auth/signin")
    suspend fun postSignIn(
        @Body request: SignInRequestDto
    ): Response<PostSignInResponseDto>

    @GET("/api/v1/users/me")
    suspend fun getNickname(
        @Header("userId") userId: Long
    ): Response<GetNicknameResponseDto>

    @GET("/api/v1/users")
    suspend fun getNicknameList(
        @Query("keyword") keyword: String
    ): Response<GetNicknameListResponseDto>

    @PATCH("/api/v1/users")
    suspend fun patchNickname(
        @Header("userId") userId: Long,
        @Body request: NicknameRequestDto
    ): Response<PatchNicknameResponseDto>
}