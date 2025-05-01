package org.sopt.at.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.R
import org.sopt.at.models.history.HistoryEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun DeleteDialog(
    item : HistoryEntity,
    modifier : Modifier = Modifier,
    onConfirm: (HistoryEntity) -> Unit,
    onDismiss: () -> Unit
) {
    AtSoptDialog(
        modifier = modifier,
        item = item,
        textContent = {
            Text(
                text = stringResource(R.string.dialog_title_delete),
                textAlign = TextAlign.Center,
                color = MyAtSoptTheme.colors.gray2,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = modifier
                    .padding(16.dp)
            )
        },
        onConfirm = {
            onConfirm(item)
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteDialogPreview() {
    val item = HistoryEntity(
        id = 1,
        title = "솝트조아"
    )
    ATSOPTANDROIDTheme {
        DeleteDialog(
            item = item,
            onConfirm = {

            },
            onDismiss = {

            }
        )
    }
}