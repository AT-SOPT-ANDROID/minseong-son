package org.sopt.at.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import org.sopt.at.R
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.views.navigation.Screen
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun AtSoptDialog(
    screen: Screen,
    text: String,
    onTextChanged: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hintText = if (screen == Screen.History) {
        stringResource(R.string.dialog_history_hint)
    } else {
        stringResource(R.string.profile_empty_nickname)
    }

    val placeholderText = if (screen == Screen.History) {
        stringResource(R.string.dialog_history_hint)
    } else {
        stringResource(R.string.dialog_nickname_hint)
    }

    val errorMessage = if (screen == Screen.History) {
        stringResource(R.string.dialog_history_wrong)
    } else {
        stringResource(R.string.dialog_nickname_wrong)
    }

    val isError = text.isBlank()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MyAtSoptTheme.colors.dialogContainer
            )
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChanged,
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MyAtSoptTheme.colors.dialogContainer),
                label = {
                    Text(
                        text = hintText
                    )
                },
                placeholder = {
                    Text(
                        text = placeholderText
                    )
                },
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MyAtSoptTheme.colors.white
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.End
            ) {
                DialogButton(
                    text = stringResource(R.string.dialog_btn_cancel),
                    onClick = onDismiss
                )
                DialogButton(
                    text = stringResource(R.string.dialog_btn_confirm),
                    onClick = {
                        if (!isError) {
                            onConfirm(text)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier
            .fillMaxHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MyAtSoptTheme.colors.dialogContainer,
            contentColor = MyAtSoptTheme.colors.gray2,
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryDialogPreview() {
    ATSOPTANDROIDTheme {
        AtSoptDialog(
            screen = Screen.History,
            text = "",
            onTextChanged = {},
            onConfirm = {},
            onDismiss = {}
        )
    }
}
