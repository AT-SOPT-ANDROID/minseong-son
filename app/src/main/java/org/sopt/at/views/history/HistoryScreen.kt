package org.sopt.at.views.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.sopt.at.components.dialogs.DeleteDialog
import org.sopt.at.components.dialogs.DialogType
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.views.history.components.dialog.HistoryAddDialog
import org.sopt.at.views.history.components.pager.HistoryTabPagerScreen
import org.sopt.at.views.history.components.tab.HistoryTabRow
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel : HistoryViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val historyDialogState by viewModel.historyDialogState.collectAsState()
    val historyDeleteData by viewModel.historyDeleteData.collectAsState()

    val tabDataList = viewModel.getHistoryEmptyData()
    val tabDataKeyList = tabDataList.keys.toList()
    val tabDataValueList = tabDataList.values.toList()

    val pagerState = rememberPagerState(
        pageCount = { tabDataList.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    val tabIndex by remember {
        derivedStateOf {
            pagerState.currentPage
        }
    }

    LaunchedEffect(tabIndex) {
        val currentTabMenu = tabDataKeyList.getOrNull(tabIndex)
        if (currentTabMenu != null) {
            viewModel.fetchHistoryTab(currentTabMenu)
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(MyAtSoptTheme.colors.white)
            .padding(16.dp)
    ) {
        HistoryTabRow(
            tabDataList = tabDataKeyList,
            pagerState = pagerState,
            onTabSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                    viewModel.fetchHistoryTab(tabDataKeyList[index])
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .background(MyAtSoptTheme.colors.white),
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val emptyData = tabDataValueList[page]

            HistoryTabPagerScreen(
                navController = navController,
                modifier = modifier,
                emptyData = emptyData,
                historyViewModel = viewModel
            )
        }
    }

    if (historyDialogState.isVisible && historyDialogState.type == DialogType.DIALOG_TYPE_CREATE) {
        HistoryAddDialog(
            onConfirm = { item ->
                viewModel.addHistoryData(item)
                viewModel.closeHistoryDialog()
            },
            onDismiss = {
                viewModel.closeHistoryDialog()
            }
        )
    }

    if (historyDialogState.isVisible && historyDialogState.type == DialogType.DIALOG_TYPE_DELETE) {
        if (historyDeleteData != null) {
            DeleteDialog (
                item = historyDeleteData!!,
                onConfirm = { item ->
                    if (item != null) {
                        viewModel.deleteHistoryData(item)
                    }
                    viewModel.closeHistoryDialog()
                },
                onDismiss = {
                    viewModel.closeHistoryDialog()
                },
                textContent = {

                }
            )
        }
    }
}