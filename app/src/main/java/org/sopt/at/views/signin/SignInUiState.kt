package org.sopt.at.views.signin

import org.sopt.at.models.signin.SignInEntity

data class SignInUiState(
    val entity: SignInEntity = SignInEntity(),
    val loginResult: LoginResult? = null
)