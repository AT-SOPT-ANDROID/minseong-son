package org.sopt.at.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.dto.request.SignInRequestDto
import org.sopt.at.repository.AuthRepository
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.views.navigation.Screen
import org.sopt.at.views.signin.LoginResult
import org.sopt.at.views.signin.SignInEvent
import org.sopt.at.views.signin.SignInUiState
import org.sopt.at.views.signin.UiEvent
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AuthRepository
) : ViewModel() {
    //ui state
    private val _signInState = MutableStateFlow(SignInUiState())
    val signInState = _signInState.asStateFlow()

    //자동 로그인/ 즉시 최신 값 받기 - 값 바뀔 시 구독자에게 알림
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    //여러 값을 저장하거나 버퍼링 가능, 이전 값을 자동으로 받지않음, 초기값 노필요, 이벤트 소비역할
    //UI 이벤트 전달이나 일회성 작업용
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow : SharedFlow<UiEvent> = _eventFlow

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
            val result = repository.postSignIn(SignInRequestDto(inputEmail, inputPw))
            val userId = result?.data?.userId

            if (userId != null) {
                PreferenceDataStore.setUserId(context, userId)
                PreferenceDataStore.setEmail(context, inputEmail)
                PreferenceDataStore.setPassword(context, inputPw)

                _signInState.update { it.copy(loginResult = LoginResult.Success) }
                _isLoggedIn.value = true
                _eventFlow.emit(UiEvent.Navigate(Screen.Home.route))
            } else {
                _eventFlow.emit(UiEvent.ShowSnackbar("유저 정보가 없습니다."))
            }

            /*val savedEmail = PreferenceDataStore.getEmail(context).firstOrNull() ?: CommonConstants.EMPTY_STRING
            val savedPw = PreferenceDataStore.getPassword(context).firstOrNull() ?: CommonConstants.EMPTY_STRING
            val result = when {
                inputEmail == savedEmail && inputPw == savedPw -> LoginResult.Success
                inputEmail == savedEmail -> LoginResult.WrongPassword
                inputPw == savedPw -> LoginResult.WrongEmail
                else -> {
                    LoginResult.BothWrong
                }
            }*/

            /*if (result == LoginResult.Success) {
                _isLoggedIn.value = true
                _eventFlow.emit(UiEvent.Navigate(Screen.Home.route))
            } else {
                val message = when (result) {
                    LoginResult.WrongPassword -> context.getString(R.string.msg_wrong_password)
                    LoginResult.WrongEmail -> context.getString(R.string.msg_wrong_email)
                    LoginResult.BothWrong -> context.getString(R.string.msg_wrong_both)
                    else -> CommonConstants.EMPTY_STRING
                }
                _eventFlow.emit(UiEvent.ShowSnackbar(message))
            }*/


        }
    }

    private fun checkLogOut() {
        viewModelScope.launch {
            PreferenceDataStore.setEmail(context, CommonConstants.EMPTY_STRING)
            PreferenceDataStore.setPassword(context, CommonConstants.EMPTY_STRING)
            _isLoggedIn.value = false
            _eventFlow.emit(UiEvent.ShowSnackbar(context.getString(R.string.msg_log_out)))

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

            if (storedEmail != null && storedPassword != null) {
                val result = repository.postSignIn(SignInRequestDto(storedEmail, storedPassword))

                if (result?.success == true) {
                    _isLoggedIn.value = true
                }
            }
        }
    }
}