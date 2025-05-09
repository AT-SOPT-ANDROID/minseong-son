package org.sopt.at.core.utils

import android.util.Log
import androidx.annotation.StringRes
import org.sopt.at.R

object AuthValidator {
    //패턴을 미리 만들어놓기 1차과제
    /*private val idRegex = "^[a-z][a-z0-9]*$".toRegex()
    private val passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#\$%^&*]).{8,15}$".toRegex()*/

    private val idRegex = "^[a-zA-Z0-9]{8,20}$".toRegex()
    private val passwordRegex = "^[a-zA-Z0-9]{8,20}$".toRegex()
    private val nicknameRegex = "^[가-힣a-zA-Z0-9]{1,20}$".toRegex()

    fun validateId(id: String): ValidationResult {
        return when {
            //id.length !in 6..12 -> ValidationResult(false, R.string.msg_validation_wrong_email_length)
            !id.matches(idRegex) -> ValidationResult(false, R.string.msg_validation_wrong_email)
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(pw: String): ValidationResult {
        return when {
            //pw.length !in 8..15 -> ValidationResult(false, R.string.msg_validation_wrong_password_length)
            !pw.matches(passwordRegex) -> ValidationResult(false, R.string.msg_validation_wrong_password)
            else -> ValidationResult(true)
        }
    }

    fun validateNickname(nickname: String): ValidationResult {
        return when {
            //nickname.length !in 2..10 -> ValidationResult(false, R.string.msg_validation_wrong_nickname)
            !nickname.matches(nicknameRegex) -> ValidationResult(false, R.string.msg_validation_wrong_password)
            else -> ValidationResult(true)
        }
    }

    data class ValidationResult(
        val isValid: Boolean,
        @StringRes val message: Int = R.string.empty_string
    )
}