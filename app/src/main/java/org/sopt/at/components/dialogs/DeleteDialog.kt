package org.sopt.at.components.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.sopt.at.R
import org.sopt.at.models.history.HistoryEntity
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun DeleteDialog(
    item: HistoryEntity?,
    modifier: Modifier = Modifier,
    onConfirm: (HistoryEntity?) -> Unit,
    onDismiss: () -> Unit,
    textContent: @Composable () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MyAtSoptTheme.colors.black)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                textContent()

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Button(
                        onClick = onDismiss,
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MyAtSoptTheme.colors.black,
                            contentColor = MyAtSoptTheme.colors.gray2,
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_btn_cancel),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Text(
                        text = stringResource(R.string.dialog_btn_divider),
                        fontSize = 14.sp,
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(vertical = 16.dp)
                            .background(color = MyAtSoptTheme.colors.gray2)
                    )

                    Button(
                        onClick = {
                            onConfirm(item)
                        },
                        shape = RectangleShape,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MyAtSoptTheme.colors.black,
                            contentColor = MyAtSoptTheme.colors.gray2,
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_btn_confirm),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Preview(showBackground = true)
@Composable
fun DeleteDialogPreview() {
    org.sopt.designsystem.theme.AtSpotANDROIDTheme {
        DeleteDialog (
            item = null,
            modifier = Modifier,
            onConfirm = {
            },
            onDismiss = {
            },
            textContent = {
                Text(
                    text = stringResource(R.string.dialog_title_delete),
                    textAlign = TextAlign.Center,
                    color = MyAtSoptTheme.colors.gray2,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        )
    }
}