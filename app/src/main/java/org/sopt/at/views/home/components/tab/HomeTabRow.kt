package org.sopt.at.views.home.components.tab

import android.util.Log
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
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabRow(
    modifier: Modifier = Modifier,
    pagerState : PagerState,
    tabDataList : List<HomeTabEntity> = emptyList(),
    onTabSelected : (Int, HomeTabEntity) -> Unit,
) {
    val currentIndex = pagerState.currentPage

    SecondaryTabRow(
        selectedTabIndex = currentIndex,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                color = Color.Transparent
            )
        },
        divider = {}
    ) {
        tabDataList.forEachIndexed { index, value ->
            Tab(selected = currentIndex == index,
                onClick = {
                    onTabSelected(index, value)
                }
            ) {
                Text(
                    text = value.tab,
                    color = Color.White,
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
fun AtSoptTabPreview() {
    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    ATSOPTANDROIDTheme {
        HomeTabRow(
            modifier = Modifier,
            pagerState = pagerState,
            tabDataList = emptyList()
        ) { index, value ->
            Log.e("HomeTabRow", "index $index, value $value")
        }
    }
}