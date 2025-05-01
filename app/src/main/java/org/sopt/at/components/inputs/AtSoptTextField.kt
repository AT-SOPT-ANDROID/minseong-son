package org.sopt.at.components.inputs

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.R
import org.sopt.at.views.signin.LoginFieldType
import org.sopt.designsystem.theme.MyAtSoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtSoptTextField(
    value: String,
    type: LoginFieldType,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    onIconClick: () -> Unit,
    focusRequester: FocusRequester = FocusRequester(),
    nextFocusRequester: FocusRequester? = null,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var passwordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onTextChange,
        singleLine = true,
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = when (type) {
                LoginFieldType.EMAIL -> KeyboardType.Email
                LoginFieldType.PASSWORD -> KeyboardType.Password
            },
            imeAction = if (type == LoginFieldType.EMAIL) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions (
            onNext = { nextFocusRequester?.requestFocus() },
            onDone = { focusManager.clearFocus() }
        ),
        visualTransformation = if (type == LoginFieldType.PASSWORD && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
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
                        text = if (type == LoginFieldType.EMAIL) {
                            stringResource(R.string.msg_email)
                        } else {
                            stringResource(R.string.msg_password)
                        },
                        color = MyAtSoptTheme.colors.gray2.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    when (type) {
                        LoginFieldType.EMAIL -> {
                            if (value.isNotEmpty()) {
                                IconButton(onClick = onIconClick) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(R.string.btn_clear),
                                        tint = MyAtSoptTheme.colors.black
                                    )
                                }
                            }
                        }
                        LoginFieldType.PASSWORD -> {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        id = if (passwordVisible) R.drawable.baseline_remove_red_eye_24
                                        else R.drawable.eye_off
                                    ),
                                    contentDescription = if (passwordVisible) {
                                        stringResource(R.string.password_visible_content_description)
                                    } else {
                                        stringResource(R.string.password_gone_content_description)
                                    },
                                    tint = MyAtSoptTheme.colors.black
                                )
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MyAtSoptTheme.colors.buttonGray,
                    unfocusedContainerColor = MyAtSoptTheme.colors.buttonGray,
                    focusedIndicatorColor = MyAtSoptTheme.colors.buttonGray,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
                visualTransformation = if (type == LoginFieldType.PASSWORD && !passwordVisible) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                }
            )
        }
    )
}