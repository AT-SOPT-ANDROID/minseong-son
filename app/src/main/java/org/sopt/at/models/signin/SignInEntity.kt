package org.sopt.at.models.signin

import org.sopt.at.core.common.CommonConstants

data class SignInEntity(
    val email : String = CommonConstants.EMPTY_STRING,
    val password : String = CommonConstants.EMPTY_STRING
)
