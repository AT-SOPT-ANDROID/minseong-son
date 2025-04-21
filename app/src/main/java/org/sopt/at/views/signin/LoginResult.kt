package org.sopt.at.views.signin

sealed class LoginResult {
    data object Success : LoginResult()
    data object WrongPassword : LoginResult()
    data object WrongEmail : LoginResult()
    data object BothWrong : LoginResult()
    data object LogOut : LoginResult()
}