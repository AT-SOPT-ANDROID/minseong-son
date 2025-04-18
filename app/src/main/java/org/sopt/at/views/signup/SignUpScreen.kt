package org.sopt.at.views.signup

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.sopt.at.R
import org.sopt.at.components.inputs.AtSoptTextField
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.LoginFieldType
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.viewmodels.SignUpViewModel
import org.sopt.at.views.navigation.Route
import org.sopt.at.views.signin.SignInScreen

@Composable
fun SignUpScreen(
    modifier : Modifier = Modifier,
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val signUpState by signUpViewModel.signUpUiState.collectAsState()
    val screenType by signUpViewModel.signUpTypeState.collectAsState()
    val toastMsg by signUpViewModel.toastMessage.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(toastMsg) {
        if (!toastMsg.isNullOrEmpty()) {
            Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
            signUpViewModel.fetchToastMessage()
        }
    }

    LaunchedEffect(signUpState.signUpResult) {
        if (signUpState.signUpResult == SignUpResult.Success && screenType) {
            PreferenceDataStore.setEmail(context, signUpState.entity.email)
            PreferenceDataStore.setPassword(context, signUpState.entity.password)

            navController.navigate(Route.SIGN_IN) {
                popUpTo(Route.SIGN_UP) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if (!screenType) {
                stringResource(R.string.input_signup_email_title)
            } else {
                stringResource(R.string.input_signup_password_title)
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = modifier.height(8.dp))

        AtSoptTextField(
            value = if (!screenType) {
                signUpState.entity.email
            } else {
                signUpState.entity.password
            },
            type = if (!screenType) {
                LoginFieldType.EMAIL
            } else {
                LoginFieldType.PASSWORD
            },
            onTextChange = {
                if (!screenType) {
                    signUpViewModel.onEvent(SignUpEvent.EmailChanged(it))
                } else {
                    signUpViewModel.onEvent(SignUpEvent.PasswordChanged(it))
                }
            },
            onIconClick = {
                if (!screenType) {
                    signUpViewModel.onEvent(SignUpEvent.EmailChanged(CommonConstants.EMPTY_STRING))
                }
            }
        )

        Text(
            text = if (!screenType) {
                stringResource(R.string.msg_sign_up_email)
            } else {
                stringResource(R.string.msg_sign_up_password)
            },
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .alpha(0.5f)
                .padding(top = 8.dp, start = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (!screenType) {
                    signUpViewModel.onEvent(SignUpEvent.SubmitClicked)
                } else {
                    signUpViewModel.onEvent(SignUpEvent.SubmitClicked)
                }
            },
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            interactionSource = interactionSource
        ) {
            if (!screenType) {
                Text(
                    text = stringResource(R.string.btn_next),
                    fontSize = 14.sp,
                    color = Color.White
                )
            } else {
                Text(
                    text = stringResource(R.string.btn_complete),
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    val navController = rememberNavController()

    ATSOPTANDROIDTheme {
        SignInScreen(
            navController = navController
        )
    }
}
