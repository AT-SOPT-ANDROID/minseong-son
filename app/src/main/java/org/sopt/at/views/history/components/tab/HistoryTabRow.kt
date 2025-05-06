package org.sopt.at.views.history.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.designsystem.theme.MyAtSoptTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTabRow(
    tabDataList: List<String>,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentIndex = pagerState.currentPage

    SecondaryTabRow(
        modifier = modifier,
        selectedTabIndex = currentIndex,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = modifier.tabIndicatorOffset(currentIndex),
                color = MyAtSoptTheme.colors.black
            )
        },
        divider = {} //빈 컴포저블을 넣어 구분선 제거
    ) {
        tabDataList.forEachIndexed { index, value ->
            Tab(selected = currentIndex == index,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Text(
                    text = value,
                    color = MyAtSoptTheme.colors.black,
                    fontWeight = if (currentIndex == index) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    },
                    modifier = modifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryTabRowPreview() {
    val pagerState = rememberPagerState (
        pageCount = { 2 },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    ATSOPTANDROIDTheme {
        HistoryTabRow(
            tabDataList = listOf("시리즈", "영화"),
            pagerState = pagerState,
            onTabSelected = {}
        )
    }
}