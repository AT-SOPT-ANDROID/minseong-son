package org.sopt.at.views.search.components.edittext

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants
import org.sopt.designsystem.theme.MyAtSoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchEditText(
    searchValue: String,
    focusRequester: FocusRequester,
    onTextChange: (String) -> Unit,
    onDone : (Boolean) -> Unit,
    onClear : () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = searchValue,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .background(
                color = Color.Transparent,
            )
            .focusRequester(focusRequester)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        textStyle = TextStyle(
            color = MyAtSoptTheme.colors.black,
            fontSize = 24.sp
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone(true)
            }
        ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = searchValue,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = false,
                label = null,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = MyAtSoptTheme.colors.gray2,
                    focusedContainerColor = MyAtSoptTheme.colors.gray2
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.hint_search),
                        color = MyAtSoptTheme.colors.gray2.copy(alpha = 0.5f),
                        fontSize = 24.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = MyAtSoptTheme.colors.black
                    )
                },
                trailingIcon = {
                    if (searchValue.isNotEmpty()) {
                        IconButton(onClick = {
                            onTextChange(CommonConstants.EMPTY_STRING)
                            onClear()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.btn_clear),
                                tint = MyAtSoptTheme.colors.black
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(8.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchEditPreview() {
    val focusRequester = remember { FocusRequester() }
    SearchEditText(
        searchValue = "",
        focusRequester = focusRequester,
        onTextChange = {},
        onDone = {},
        onClear = {}
    )
}