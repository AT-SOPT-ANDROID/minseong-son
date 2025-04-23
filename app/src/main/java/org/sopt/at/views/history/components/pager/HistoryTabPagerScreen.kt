package org.sopt.at.views.history.components.pager

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import org.sopt.at.R
import org.sopt.at.components.dialogs.DialogType
import org.sopt.at.views.history.components.tab.HistoryTabData
import org.sopt.at.models.history.HistoryEmptyEntity
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.views.history.components.itemgrid.HistoryLazyGrid
import org.sopt.at.views.navigation.Screen

@Composable
fun HistoryTabPagerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    emptyData : HistoryEmptyEntity?,
    historyViewModel: HistoryViewModel = hiltViewModel()
) {
    val tab by historyViewModel.historyTab.collectAsState()
    val historyList by historyViewModel.historyDataList.collectAsState(initial = emptyList())

    if (historyList.isEmpty() || tab != HistoryTabData.TAB_SERIES) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = emptyData?.title.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Text(
                text = emptyData?.description.toString(),
                fontSize = 12.sp,
                color = Color.White,
                modifier = modifier
                    .alpha(0.5f)
            )

            Spacer(modifier = modifier.height(36.dp))

            Text(
                text = if (emptyData?.id!! % 2 == 0) {
                    stringResource(id = R.string.text_history_empty_view)
                } else {
                    stringResource(id = R.string.text_history_empty_movie)
                },
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier
                    .border(1.dp, Color.White, RectangleShape)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.Start)
                    .clickable {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.History.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
            )
        }
    } else {
        HistoryLazyGrid(
            modifier = modifier
                .fillMaxSize(),
            historyList = historyList
        ) {
            if (it != null) {
                historyViewModel.fetchHistoryData(it)
                historyViewModel.openHistoryDialog(DialogType.DIALOG_TYPE_DELETE)
            }
        }
    }
}