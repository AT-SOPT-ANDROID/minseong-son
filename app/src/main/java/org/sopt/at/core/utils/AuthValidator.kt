package org.sopt.at.core.utils

import android.content.Context
import android.util.Log
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants

object AuthValidator {
    fun validateId(id: String): ValidationResult {
        Log.e("validateId", "validateId: $id")
        return when {
            id.length !in 6..12 -> ValidationResult(false, R.string.msg_validation_wrong_email_length.toString())
            !id.matches("^[a-z][a-z0-9]*$".toRegex()) -> ValidationResult(false, R.string.msg_validation_wrong_email.toString())
            else -> ValidationResult(true)
        }
    }

    fun validatePassword(pw: String): ValidationResult {
        return when {
            pw.length !in 8..15 -> ValidationResult(false, R.string.msg_validation_wrong_password_length.toString())
            !pw.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#\$%^&*]).{8,15}$".toRegex()) ->
                ValidationResult(false, R.string.msg_validation_wrong_password.toString())
            else -> ValidationResult(true)
        }
    }

    data class ValidationResult(val isValid: Boolean, val message: String = CommonConstants.EMPTY_STRING)
}