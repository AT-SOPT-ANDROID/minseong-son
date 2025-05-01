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
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun ExitDialog(
    modifier : Modifier = Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AtSoptDialog(
        item = null,
        modifier = modifier,
        onConfirm = {
            onConfirm()
        },
        onDismiss = {
            onDismiss()
        },
        textContent = {
            Text(
                text = stringResource(R.string.dialog_title_exit),
                textAlign = TextAlign.Center,
                color = MyAtSoptTheme.colors.gray2,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = modifier
                    .padding(16.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ExitDialogPreview() {
    ATSOPTANDROIDTheme {
        ExitDialog(onConfirm = {}, onDismiss = {})
    }
}