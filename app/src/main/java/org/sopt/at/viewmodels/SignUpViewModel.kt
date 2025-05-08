package org.sopt.at.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.at.core.utils.AuthValidator
import org.sopt.at.models.dto.request.SignInRequestDto
import org.sopt.at.models.dto.request.SignUpRequestDto
import org.sopt.at.models.signin.SignInEntity
import org.sopt.at.repository.AuthRepository
import org.sopt.at.views.signup.ScreenType
import org.sopt.at.views.signup.SignUpEvent
import org.sopt.at.views.signup.SignUpResult
import org.sopt.at.views.signup.SignUpUiState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AuthRepository
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _screenTypeState = MutableStateFlow(ScreenType.EMAIL)
    val screenTypeState = _screenTypeState.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage = _toastMessage.asStateFlow()

    fun fetchToastMessage() {
        _toastMessage.value = null
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                _signUpUiState.update {
                    it.copy(entity = it.entity.copy(email = event.email))
                }
            }

            is SignUpEvent.PasswordChanged -> {
                _signUpUiState.update {
                    it.copy(entity = it.entity.copy(password = event.password))
                }
            }

            is SignUpEvent.NicknameChanged -> {
                _signUpUiState.update {
                    it.copy(entity = it.entity.copy(nickname = event.nickname))
                }
            }

            SignUpEvent.SubmitClicked -> {
                /*if (!_signUpTypeState.value) {
                    handleEmailChanged(_signUpUiState.value.entity.email)
                } else {
                    handlePasswordChanged(_signUpUiState.value.entity.password)
                }*/
                when (_screenTypeState.value) {
                    ScreenType.EMAIL -> handleEmailChanged(_signUpUiState.value.entity.email)
                    ScreenType.PASSWORD -> handlePasswordChanged(_signUpUiState.value.entity.password)
                    ScreenType.NICKNAME -> handleNicknameChanged(_signUpUiState.value.entity.nickname)
                }
            }
        }
    }

    private fun handleEmailChanged(email: String) {
        val isEmailValid = AuthValidator.validateId(email)

        if (!isEmailValid.isValid) {
            _signUpUiState.update {
                it.copy(
                    signUpResult = SignUpResult.WrongEmail
                )
            }

            _toastMessage.update {
                context.getString(isEmailValid.message)
            }

            return
        }

        val updatedEntity = _signUpUiState.value.entity.copy(email = email)
        updateState(updatedEntity, checkPassword = false, checkNickName = false)
        _screenTypeState.value = ScreenType.PASSWORD
        _signUpUiState.update {
            it.copy(
                signUpResult = SignUpResult.WrongPassword
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val isPasswordValid = AuthValidator.validatePassword(password)

        if (!isPasswordValid.isValid) {
            _signUpUiState.update {
                it.copy(
                    signUpResult = SignUpResult.WrongPassword
                )
            }

            _toastMessage.update {
                context.getString(isPasswordValid.message)
            }

            return
        }

        val updatedEntity = _signUpUiState.value.entity.copy(password = password)
        updateState(updatedEntity, checkEmail = false, checkNickName = false)
        _screenTypeState.value = ScreenType.NICKNAME
        _signUpUiState.update {
            it.copy(
                signUpResult = SignUpResult.WrongNickname
            )
        }
    }

    private fun handleNicknameChanged(nickname: String) {
        val isNicknameValid = AuthValidator.validateNickname(nickname)
        if (!isNicknameValid.isValid) {
            _signUpUiState.update {
                it.copy(
                    signUpResult = SignUpResult.WrongNickname
                )
            }

            _toastMessage.update {
                context.getString(isNicknameValid.message)
            }

            return
        }

        val updatedEntity = _signUpUiState.value.entity.copy(nickname = nickname)
        updateState(updatedEntity, checkEmail = false, checkPassword = false)
        _screenTypeState.value = ScreenType.NICKNAME
        _signUpUiState.update {
            it.copy(
                signUpResult = SignUpResult.WrongNickname
            )
        }

        signUp(updatedEntity)
    }


    private fun updateState(
        newEntity: SignInEntity,
        checkEmail: Boolean = true,
        checkPassword: Boolean = true,
        checkNickName : Boolean = true
    ) {
        _signUpUiState.update { state ->
            state.copy(
                entity = newEntity,
                signUpResult = when {
                    checkEmail && newEntity.email.isBlank() -> SignUpResult.WrongEmail
                    checkPassword && newEntity.password.isBlank() -> SignUpResult.WrongPassword
                    checkNickName && newEntity.nickname.isBlank() -> SignUpResult.WrongNickname
                    else -> SignUpResult.Success
                }
            )
        }
    }

    private fun signUp(entity: SignInEntity) {
        viewModelScope.launch {
            val result = repository.postSignUp(SignUpRequestDto(
                loginId = entity.email,
                password = entity.password,
                nickname = entity.nickname
            ))

            if (result != null) {
                _signUpUiState.update {
                    it.copy(signUpResult = SignUpResult.Success)
                }
                _toastMessage.update {
                    result.message
                }
            } else {
                _signUpUiState.update {
                    it.copy(signUpResult = SignUpResult.Failure)
                }
                _toastMessage.update {
                    "회원가입 실패"
                }
            }

            /*try {
                val response = repository.postSignUp(SignUpRequestDto(
                    loginId = entity.email,
                    password = entity.password,
                    nickname = entity.nickname
                ))

                if (response.isSuccessful) {
                    val successMsg = response.body()?.message
                    _signUpUiState.update {
                        it.copy(signUpResult = SignUpResult.Success)
                    }
                    _toastMessage.update {
                        successMsg
                    }

                } else {
                    val errorMsg = response.body()?.message
                    _signUpUiState.update {
                        it.copy(signUpResult = SignUpResult.Failure)
                    }
                    _toastMessage.update {
                        errorMsg
                    }
                }

            } catch (e: Exception) {
                _signUpUiState.update {
                    it.copy(signUpResult = SignUpResult.Failure)
                }
                _toastMessage.update {
                    e.message ?: "회원가입 실패"
                }
            }*/
        }
    }
}