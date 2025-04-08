package org.sopt.at.views.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopt.at.R
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.ui.theme.ButtonGrayColor
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.views.main.MainActivity
import org.sopt.at.views.main.noRippleClickable
import org.sopt.at.views.signup.SignUpActivity

enum class LoginFieldType {
    EMAIL,
    PASSWORD
}

class SignInActivity : ComponentActivity() {

    private lateinit var signUpLauncher: ActivityResultLauncher<Intent>
    private var signUpEmail : String? = null
    private var signUpPw : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        /*lifecycleScope.launch {
            PreferenceDataStore.getEmail(this@SignInActivity).collect { email ->
                signUpEmail = email
            }
        }

        lifecycleScope.launch {
            PreferenceDataStore.getPassword(this@SignInActivity).collect { pw ->
                signUpPw = pw
            }
        }*/

        lifecycleScope.launch {
            val email = PreferenceDataStore.getEmail(this@SignInActivity).first()
            val pw = PreferenceDataStore.getPassword(this@SignInActivity).first()

            if (email?.isNotEmpty() == true && pw?.isNotEmpty() == true) {
                Toast.makeText(this@SignInActivity, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                finish()
            }
        }

        signUpLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                signUpEmail = result.data?.getStringExtra("email")
                signUpPw = result.data?.getStringExtra("pw")

                // 여기서 받은 email, pw 활용
                lifecycleScope.launch {
                    signUpEmail?.let { PreferenceDataStore.setEmail(this@SignInActivity, it) }
                    signUpPw?.let { PreferenceDataStore.setPassword(this@SignInActivity, it) }
                }
            }
        }

        setContent {
            ATSOPTANDROIDTheme {
                val interactionSource = remember { MutableInteractionSource() }
                Scaffold(
                    topBar = {
                        Row (
                            modifier = Modifier.fillMaxWidth()
                                .padding(WindowInsets.statusBars.asPaddingValues())
                        ){
                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                    contentDescription = "뒤로가기"
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignInActivityScreen(
                        interactionSource = interactionSource,
                        innerPadding,
                        onSignUpClick = {
                            val intent = Intent(this, SignUpActivity::class.java)
                            signUpLauncher.launch(intent)
                        },
                        onLoginClick = { email, pw ->
                            when {
                                email == signUpEmail && pw == signUpPw -> {
                                    val intent = Intent(this@SignInActivity, MainActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        putExtra("email", signUpEmail)
                                    }
                                    startActivity(intent)
                                    null // 성공 시 snackbar 안 띄움
                                }
                                email == signUpEmail -> {
                                    "비밀번호가 일치하지 않습니다."
                                }
                                pw == signUpPw -> {
                                    "이메일이 일치하지 않습니다."
                                }
                                else -> {
                                    "이메일과 비밀번호가 모두 일치하지 않습니다."
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignInActivityScreen(
    interactionSource : MutableInteractionSource,
    padding : PaddingValues,
    onSignUpClick : () -> Unit,
    onLoginClick : (String, String) -> String?
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val emailFocusRequester = remember { FocusRequester() }
    val pwFocusRequester = remember { FocusRequester() }

    var emailValue by remember {
        mutableStateOf("")
    }

    var pwValue by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.padding(padding).padding(WindowInsets.ime.asPaddingValues()),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "TVING ID 로그인",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            SignInEditText(
                value = emailValue,
                type = LoginFieldType.EMAIL,
                onTextChange = { emailValue = it },
                onIconClick = { emailValue = "" },
                focusRequester = emailFocusRequester,
                nextFocusRequester = pwFocusRequester
            )
            Spacer(modifier = Modifier.height(12.dp))

            SignInEditText(
                value = pwValue,
                type = LoginFieldType.PASSWORD,
                onTextChange = { pwValue = it },
                onIconClick = { },
                focusRequester = pwFocusRequester
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = emailValue.isNotEmpty() && pwValue.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RectangleShape,
                onClick = {
                    val resultMessage = onLoginClick(emailValue, pwValue)
                    if (resultMessage != null) {
                        scope.launch {
                            snackBarHostState.showSnackbar(resultMessage)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(emailValue.isNotEmpty() && pwValue.isNotEmpty()) {
                        Color.Red
                    } else {
                        ButtonGrayColor
                    }
                ),
                interactionSource = interactionSource
            ) {
                Text(
                    text = "로그인하기",
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            AccountSupport(
                onSignUpClick = { onSignUpClick() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
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
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally).alpha(0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInEditText(
    value: String,
    type: LoginFieldType,
    onTextChange: (String) -> Unit,
    onIconClick: () -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var pwIconState by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = if (type == LoginFieldType.EMAIL) ImeAction.Next else ImeAction.Done,
            keyboardType = if (type == LoginFieldType.EMAIL) KeyboardType.Email else KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                nextFocusRequester?.requestFocus()
            },
            onDone = {
                focusManager.clearFocus()
            }
        ),
        visualTransformation = when {
            type == LoginFieldType.PASSWORD && !pwIconState -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                interactionSource = interactionSource,
                isError = false,
                label = null,
                placeholder = {
                    Text(
                        text = if (type == LoginFieldType.EMAIL) "아이디" else "비밀번호",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    if (type == LoginFieldType.EMAIL && value.isNotEmpty()) {
                        IconButton(onClick = { onIconClick() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    } else if (type == LoginFieldType.PASSWORD) {
                        IconButton(onClick = { pwIconState = !pwIconState }) {
                            Icon(
                                painter = painterResource(
                                    if (pwIconState) R.drawable.baseline_remove_red_eye_24
                                    else R.drawable.eye_off
                                ),
                                contentDescription = if (pwIconState) "pw eye_on" else "pw eye_off",
                                tint = Color.LightGray
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = ButtonGrayColor,
                    focusedIndicatorColor = ButtonGrayColor,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                visualTransformation = when {
                    type == LoginFieldType.PASSWORD && !pwIconState -> PasswordVisualTransformation()
                    else -> VisualTransformation.None
                },
            )
        }
    )
}

@Composable
fun AccountSupport(
    onSignUpClick : () -> Unit
) {

    Row (
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "아이디 찾기",
            fontSize = 14.sp,
            color = Color.Gray
        )

        VerticalDivider(modifier = Modifier.height(16.dp))

        Text(
            text = "비밀번호 찾기",
            fontSize = 14.sp,
            color = Color.Gray
        )

        VerticalDivider(modifier = Modifier.height(16.dp))

        Text(
            text = "회원가입",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.noRippleClickable {
                onSignUpClick()
            }
        )
    }
}