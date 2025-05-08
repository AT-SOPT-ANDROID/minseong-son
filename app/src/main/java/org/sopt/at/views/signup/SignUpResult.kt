package org.sopt.at.views.signup

sealed class SignUpResult {
    data object Success : SignUpResult()
    data object WrongPassword : SignUpResult()
    data object WrongEmail : SignUpResult()
    data object WrongNickname : SignUpResult()
    data object Failure : SignUpResult()
}