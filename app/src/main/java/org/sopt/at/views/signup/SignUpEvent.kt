package org.sopt.at.views.signup

sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data object SubmitClicked : SignUpEvent()
    data class NicknameChanged(val nickname: String) : SignUpEvent()
}