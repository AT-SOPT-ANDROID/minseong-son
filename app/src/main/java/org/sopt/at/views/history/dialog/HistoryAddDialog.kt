package org.sopt.at.views.history.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.history.HistoryEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.ui.theme.DialogContainerColor
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.views.home.common.HomeItemTypeDescription

@Composable
fun HistoryAddDialog(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    onConfirm: (HistoryEntity) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val historyTitle by historyViewModel.historyTitle.collectAsState()

    var isError by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(historyTitle) {
        isError = historyTitle.isNullOrEmpty()
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DialogContainerColor)
        ) {
            Card (
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = DialogContainerColor
                )
            ) {
                OutlinedTextField(
                    value = historyTitle ?: CommonConstants.EMPTY_STRING,
                    onValueChange = {
                        historyViewModel.setHistoryTitle(it)
                    },
                    singleLine = false,
                    textStyle = TextStyle (
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(DialogContainerColor),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.dialog_history_hint)
                        )
                    },
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.dialog_history_wrong),
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                )


                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Button(
                        onClick = {
                            historyViewModel.clearHistoryTitle()
                            onDismiss()
                        },
                        shape = RectangleShape,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DialogContainerColor,
                            contentColor = Color.LightGray,
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.dialog_btn_cancel),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }


                    Button(
                        onClick = {
                            if (!isError) {
                                historyViewModel.clearHistoryTitle()
                                onConfirm(
                                    HistoryEntity(
                                        id = 0,
                                        title = historyTitle,
                                        type = HomeItemTypeDescription.TYPE_ONLY
                                    )
                                )
                            }
                        },
                        shape = RectangleShape,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DialogContainerColor,
                            contentColor = Color.LightGray,
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

@Preview(showBackground = true)
@Composable
fun HistoryAddDialogPreview() {
    ATSOPTANDROIDTheme {
        HistoryAddDialog()
    }
}