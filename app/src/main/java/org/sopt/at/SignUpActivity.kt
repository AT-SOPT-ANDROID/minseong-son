package org.sopt.at

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.ui.theme.ButtonGrayColor

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            ATSOPTANDROIDTheme {
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
                    SignUpActivityScreen(
                        paddingValues = innerPadding
                    ) { email, pw ->
                        Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                        val resultIntent = Intent().apply {
                            putExtra("email", email)
                            putExtra("pw", pw)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpActivityScreen(
    paddingValues: PaddingValues,
    onClickSignUp : (String, String) -> Unit
) {
    val context = LocalContext.current

    var emailValue by remember {
        mutableStateOf("")
    }
    var pwValue by remember {
        mutableStateOf("")
    }

    var isValueCheck by remember {
        mutableStateOf("")
    }

    var screenType by remember {  //false - 이메일, true - 비번
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var pwIconState by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(WindowInsets.ime.asPaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if (!screenType) {
                "아이디를 입력해주세요."
            } else {
                "비밀번호를 입력해주세요."
            },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = if (!screenType) {
                emailValue
            } else {
                pwValue
            },
            onValueChange = {
                val filtered = it.filter { char ->
                    if (!screenType) {
                        char.toString().matches(Regex("[a-zA-Z0-9]"))
                    } else {
                        char.toString().matches(Regex("[a-zA-Z0-9~!@#\$%^&*]"))
                    }
                }

                if (!screenType) {
                    if (filtered != it) {
                        Toast.makeText(context, "영문 소문자와 숫자만 사용 가능해요.", Toast.LENGTH_SHORT).show()
                    }
                    emailValue = filtered
                    isValueCheck = emailValueCheck(filtered)
                } else {
                    if (filtered != it) {
                        Toast.makeText(context, "영문, 숫자, 특수문자(~!@#\$%^&*)만 사용 가능해요.", Toast.LENGTH_SHORT).show()
                    }
                    pwValue = filtered
                    isValueCheck = pwValueCheck(filtered).toString()
                }
            },
            singleLine = true,
            maxLines = 1,
            visualTransformation = when {
                screenType && !pwIconState -> {
                    PasswordVisualTransformation()
                }
                else -> VisualTransformation.None
            },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = if (!screenType) {
                        emailValue
                    } else {
                        pwValue
                    },
                    innerTextField = innerTextField,
                    enabled = true,
                    singleLine = true,
                    isError = false,
                    label = null,
                    placeholder = {
                        Text(
                            text = if (!screenType) {
                                "아이디"
                            } else {
                                "비밀번호"
                            },
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    },
                    trailingIcon = {
                        if (!screenType) {
                            if (emailValue.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        emailValue = ""
                                    },
                                    interactionSource = interactionSource
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        } else {
                            IconButton(
                                onClick = { pwIconState = !pwIconState },
                                interactionSource = interactionSource
                            ) {
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
                    //shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = ButtonGrayColor,
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                    interactionSource = interactionSource,
                    visualTransformation = when {
                        screenType && !pwIconState -> {
                            PasswordVisualTransformation()
                        }
                        else -> VisualTransformation.None
                    }
                )
            }
        )

        Text(
            text = if (!screenType) {
                "영문 소문자 또는 영문 소문자, 숫자 조합 6~12 자리"
            } else {
                "영문, 숫자, 특수문자(~!@#$%^&*) 조합 8~15자리"
            },
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.alpha(0.5f).padding(top = 8.dp, start = 16.dp).align(Alignment.Start)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (!screenType) {
                    if (isValueCheck.split(" ").first().toBoolean()) {
                        screenType = !screenType
                        isValueCheck = ""
                    } else if (!isValueCheck.split(" ").first().toBoolean() && isValueCheck.split(" ").last() == "UpperCase"){
                        Toast.makeText(context, "아이디는 영문 소문자와 숫자만 사용 가능해요.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "아이디를 6~12자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (isValueCheck.toBoolean()) {
                        if (emailValue.isNotEmpty() && pwValue.isNotEmpty()) {
                            onClickSignUp(emailValue, pwValue)
                        }
                    } else {
                        Toast.makeText(context, "8~15자리로 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            //enabled = isValueCheck.split(" ").first().toBoolean(),
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                //.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            interactionSource = interactionSource
        ) {
            Text(text = "다음", fontSize = 14.sp, color = Color.White)
        }
    }
}

fun emailValueCheck(email : String) : String {
    if (email.length in 6..12) {
        for (i in email.indices) {
            if (email[i].isUpperCase()) {
                return "false UpperCase"
            }
        }
    } else {
        return "false length"
    }

    return "true"
}

fun pwValueCheck(pw : String) : Boolean {
    return pw.length in 8..15
}