package org.sopt.at.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.at.core.utils.AuthValidator
import org.sopt.at.models.signin.SignInEntity
import org.sopt.at.views.signup.SignUpEvent
import org.sopt.at.views.signup.SignUpResult
import org.sopt.at.views.signup.SignUpUiState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _signUpTypeState = MutableStateFlow(false)
    val signUpTypeState = _signUpTypeState.asStateFlow()

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

            SignUpEvent.SubmitClicked -> {
                if (!_signUpTypeState.value) {
                    handleEmailChanged(_signUpUiState.value.entity.email)
                } else {
                    handlePasswordChanged(_signUpUiState.value.entity.password)
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
        updateState(updatedEntity, checkPassword = false)
        _signUpTypeState.value = true
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
        updateState(updatedEntity, checkEmail = false)
    }


    private fun updateState(
        newEntity: SignInEntity,
        checkEmail: Boolean = true,
        checkPassword: Boolean = true
    ) {
        _signUpUiState.update { state ->
            state.copy(
                entity = newEntity,
                signUpResult = when {
                    checkEmail && newEntity.email.isBlank() -> {
                        SignUpResult.WrongEmail
                    }

                    checkPassword && newEntity.password.isBlank() -> {
                        SignUpResult.WrongPassword
                    }

                    newEntity.email.isNotBlank() && newEntity.password.isNotBlank() -> {
                        SignUpResult.Success
                    }

                    else -> {
                        SignUpResult.Success
                    }
                }
            )
        }
    }
}