package org.sopt.at.views.signin

sealed class SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    data object SubmitClicked : SignInEvent()
    data object LogOutClicked : SignInEvent()
}