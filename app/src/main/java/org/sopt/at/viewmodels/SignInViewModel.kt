package org.sopt.at.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.views.signin.LoginResult
import org.sopt.at.views.signin.SignInEvent
import org.sopt.at.views.signin.SignInUiState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    //ui state
    private val _signInState = MutableStateFlow(SignInUiState())
    val signInState = _signInState.asStateFlow()

    //자동 로그인
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkLoggedIn()
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _signInState.update {
                    it.copy(entity = it.entity.copy(email = event.email))
                }
            }

            is SignInEvent.PasswordChanged -> {
                _signInState.update {
                    it.copy(entity = it.entity.copy(password = event.password))
                }
            }

            SignInEvent.SubmitClicked -> {
                handleLogin()
            }

            SignInEvent.LogOutClicked -> {
                checkLogOut()
            }
        }
    }

    private fun handleLogin() {
        val inputEmail = _signInState.value.entity.email
        val inputPw = _signInState.value.entity.password

        viewModelScope.launch {
            val savedEmail = PreferenceDataStore.getEmail(context).firstOrNull() ?: CommonConstants.EMPTY_STRING
            val savedPw = PreferenceDataStore.getPassword(context).firstOrNull() ?: CommonConstants.EMPTY_STRING
            val result = when {
                inputEmail == savedEmail && inputPw == savedPw -> LoginResult.Success
                inputEmail == savedEmail -> LoginResult.WrongPassword
                inputPw == savedPw -> LoginResult.WrongEmail
                else -> {
                    LoginResult.BothWrong
                }
            }

            Log.e("TAG", "handleLogin: $result")

            if (result == LoginResult.Success) {
                _isLoggedIn.value = true
            }

            _signInState.update {
                it.copy(
                    loginResult = result
                )
            }
        }
    }

    private fun checkLogOut() {
        viewModelScope.launch {
            PreferenceDataStore.setEmail(context, CommonConstants.EMPTY_STRING)
            PreferenceDataStore.setPassword(context, CommonConstants.EMPTY_STRING)
            _isLoggedIn.value = false
            _signInState.update {
                it.copy(
                    loginResult = LoginResult.LogOut
                )
            }
        }
    }


    private fun checkLoggedIn() {
        viewModelScope.launch {
            val storedEmail = PreferenceDataStore.getEmail(context).firstOrNull()
            val storedPassword = PreferenceDataStore.getPassword(context).firstOrNull()

            _isLoggedIn.value = !storedEmail.isNullOrEmpty() && !storedPassword.isNullOrEmpty()
        }
    }
}