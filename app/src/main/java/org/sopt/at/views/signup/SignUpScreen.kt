package org.sopt.at.views.signup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.sopt.at.R
import org.sopt.at.components.inputs.AtSoptTextField
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.views.signin.LoginFieldType
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.viewmodels.SignUpViewModel
import org.sopt.at.views.navigation.Screen
import org.sopt.at.views.signin.SignInScreen
import org.sopt.designsystem.theme.MyAtSoptTheme

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

    //미리 정리 해놔서 ui가 편하게
    val currentInputValue = if (!screenType) signUpState.entity.email else signUpState.entity.password
    val currentFieldType = if (!screenType) LoginFieldType.EMAIL else LoginFieldType.PASSWORD
    val currentHelperText = stringResource(
        if (!screenType) R.string.msg_sign_up_email else R.string.msg_sign_up_password
    )
    val currentTitleText = stringResource(
        if (!screenType) R.string.input_signup_email_title else R.string.input_signup_password_title
    )
    val buttonText = stringResource(
        if (!screenType) R.string.btn_next else R.string.btn_complete
    )

    // 컴포저블 내부에서 매번 새로 생성되는 현상 방지
    val onSubmitClick = remember(signUpViewModel, screenType) {
        {
            signUpViewModel.onEvent(SignUpEvent.SubmitClicked)
        }
    }

    val onIconClick = remember(screenType) {
        {
            signUpViewModel.onEvent(SignUpEvent.EmailChanged(CommonConstants.EMPTY_STRING))
        }
    }

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

            navController.navigate(Screen.SignIn.route) {
                popUpTo(Screen.SignUp.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MyAtSoptTheme.colors.white)
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = currentTitleText,
            color = MyAtSoptTheme.colors.white,
            style = MyAtSoptTheme.typography.title
        )

        Spacer(modifier = modifier.height(8.dp))

        AtSoptTextField(
            value = currentInputValue,
            type = currentFieldType,
            onTextChange = {
                if (!screenType) {
                    signUpViewModel.onEvent(SignUpEvent.EmailChanged(it))
                } else {
                    signUpViewModel.onEvent(SignUpEvent.PasswordChanged(it))
                }
            },
            onIconClick = onIconClick
        )

        Text(
            text = currentHelperText,
            color = MyAtSoptTheme.colors.white,
            fontSize = 12.sp,
            modifier = Modifier
                .alpha(0.5f)
                .padding(top = 8.dp, start = 16.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSubmitClick,/*{
                if (!screenType) {
                    signUpViewModel.onEvent(SignUpEvent.SubmitClicked)
                } else {
                    signUpViewModel.onEvent(SignUpEvent.SubmitClicked)
                }
            },*/
            shape = RectangleShape,
            border = BorderStroke(1.dp, MyAtSoptTheme.colors.white),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            interactionSource = interactionSource
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp,
                color = MyAtSoptTheme.colors.white
            )
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
