package org.sopt.at.views.signin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.sopt.at.R
import org.sopt.at.components.inputs.AtSoptTextField
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.extensions.noRippleClickable
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.navigation.Screen
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController : NavController,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }

    val snackBarHostState = remember { SnackbarHostState() }
    val emailFocusRequester = remember { FocusRequester() }
    val pwFocusRequester = remember { FocusRequester() }

    val uiState by signInViewModel.signInState.collectAsState()

    val isEnabled by remember(uiState) {
        derivedStateOf {
            uiState.entity.email.isNotEmpty() && uiState.entity.password.isNotEmpty()
        }
    }

    //람다함수를 따로 관리
    val onSignUpClick = remember(navController) {
        {
            navController.navigate(Screen.SignUp.route) {
                launchSingleTop = true
            }
        }
    }

    val onSubmitClick = remember(signInViewModel) {
        {
            Log.e("TAG", "SignInScreen: SignIn")
            signInViewModel.onEvent(SignInEvent.SubmitClicked)
        }
    }
    /*LaunchedEffect(snackbarMassage) {
        if (snackbarMassage.isNotEmpty() && snackbarMassage.isNotBlank()) {
            snackBarHostState.showSnackbar(
                message = snackbarMassage
            )
            snackbarMassage = CommonConstants.EMPTY_STRING
        }
    }*/

    /*LaunchedEffect(uiState.loginResult) {
        when (uiState.loginResult) {
            LoginResult.Success -> {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.SignIn.route) { // 뒤로가기 방지 로그인 루트 까지 삭제 - 자기포함
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }

            LoginResult.WrongPassword -> {
                snackbarMassage = context.getString(R.string.msg_wrong_password)
            }

            LoginResult.WrongEmail -> {
                snackbarMassage = context.getString(R.string.msg_wrong_email)
            }

            LoginResult.BothWrong -> {
                snackbarMassage = context.getString(R.string.msg_wrong_both)
            }

            LoginResult.LogOut -> {
                snackbarMassage = context.getString(R.string.msg_log_out)
            }

            null -> {
            }
        }
    }*/
    //side effect 따로 관리
    LaunchedEffect(Unit) {
        signInViewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(Screen.SignIn.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }


    Scaffold(
        modifier = modifier.padding(WindowInsets.ime.asPaddingValues()),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .background(MyAtSoptTheme.colors.white)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = stringResource(R.string.app_name_login),
                color = MyAtSoptTheme.colors.black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = modifier.height(16.dp))

            AtSoptTextField(
                value = uiState.entity.email,
                type = LoginFieldType.EMAIL,
                onTextChange = {
                    signInViewModel.onEvent(SignInEvent.EmailChanged(it))
                },
                onIconClick = {
                    signInViewModel.onEvent(SignInEvent.EmailChanged(CommonConstants.EMPTY_STRING))
                },
                focusRequester = emailFocusRequester,
                nextFocusRequester = pwFocusRequester
            )

            Spacer(modifier = modifier.height(12.dp))

            AtSoptTextField(
                value = uiState.entity.password,
                type = LoginFieldType.PASSWORD,
                onTextChange = {
                    signInViewModel.onEvent(SignInEvent.PasswordChanged(it))
                },
                onIconClick = { },
                focusRequester = pwFocusRequester
            )

            Spacer(modifier = modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.btn_sign_in),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RectangleShape)
                    .background(
                        if (isEnabled) MyAtSoptTheme.colors.brand else MyAtSoptTheme.colors.buttonGray
                    )
                    .clickable(
                        enabled = isEnabled,
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onSubmitClick
                    )
                    .padding(vertical = 12.dp),
                color = MyAtSoptTheme.colors.white,
                textAlign = TextAlign.Center,
                style = MyAtSoptTheme.typography.button
            )

            Spacer(modifier = modifier.height(16.dp))

            AccountSupport(
                onSignUpClick = onSignUpClick
            )

            Spacer(modifier = modifier.height(24.dp))

            Text(
                //1회성 텍스트
                text = buildAnnotatedString {
                    append("이 사이트는 Google reCAPCHA로 보호되며,\n")

                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("Google 개인정보 처리방침")
                    }

                    append("과 ")

                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("서비스 약관")
                    }

                    append("이 적용됩니다.")
                },
                fontSize = 14.sp,
                color = MyAtSoptTheme.colors.gray3,
                modifier = modifier.align(Alignment.CenterHorizontally).alpha(0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AccountSupport(
    modifier: Modifier = Modifier,
    onSignUpClick : () -> Unit
) {
    Row (
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.btn_find_id),
            fontSize = 14.sp,
            color = MyAtSoptTheme.colors.gray3
        )

        VerticalDivider(modifier = modifier.height(16.dp))

        Text(
            text = stringResource(R.string.btn_find_password),
            fontSize = 14.sp,
            color = MyAtSoptTheme.colors.gray3
        )

        VerticalDivider(modifier = modifier.height(16.dp))

        Text(
            text = stringResource(R.string.btn_sign_up),
            fontSize = 14.sp,
            color = MyAtSoptTheme.colors.gray3,
            modifier = modifier.noRippleClickable(
                onClick = {
                    onSignUpClick()
                }
            )
        )
    }
}