package org.sopt.at.views.signup

import org.sopt.at.models.signin.SignInEntity

data class SignUpUiState (
    val entity: SignInEntity = SignInEntity(),
    val signUpResult: SignUpResult? = null
)