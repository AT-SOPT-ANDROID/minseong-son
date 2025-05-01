package org.sopt.at.views.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.sopt.at.R
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.viewmodels.HomeViewModel
import org.sopt.at.views.home.components.appbar.HomeTopAppBar
import org.sopt.at.views.home.components.banner.HomeBannerLazyRow
import org.sopt.at.views.home.components.currentcontents.HomeCurrentContentsLazyRow
import org.sopt.at.views.home.components.tab.HomeTabRow
import org.sopt.at.views.home.components.toptwenty.HomeTopTwentyLazyRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    val homeBannerDataList by homeViewModel.homeBannerDataList.collectAsState()
    val homeTopTwentyDataList = homeViewModel.getHomeTopTwentyItemList()
    val homeCurrentContentsDataList = homeViewModel.getHomeCurrentContentsItemList()
    val homeTab by homeViewModel.homeTab.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val tabDataList by homeViewModel.tabDataList.collectAsState()

    val pagerState = rememberPagerState(
        pageCount = { tabDataList.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    LaunchedEffect(Unit) {
        homeViewModel.fetchHomeTab(HomeTabEntity(tab = CommonConstants.EMPTY_STRING))
    }

    LaunchedEffect(homeTab) {
        listState.animateScrollToItem(0)
    }

    LazyColumn (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        item {
            HomeTopAppBar(
                modifier = Modifier,
                navController = navController
            )
        }

        stickyHeader {
            HomeTabRow(
                modifier = modifier,
                tabDataList = tabDataList,
                pagerState = pagerState,
                onTabSelected = { index, value ->
                    coroutineScope.launch {
                        homeViewModel.fetchHomeTab(value)
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }

        item {
            HomeBannerLazyRow(
                modifier = modifier,
                homeBannerDataList = homeBannerDataList
            )
        }

        item {
            Text(
                text = stringResource(R.string.text_home_top_20),
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier,
                fontSize = 24.sp
            )

            HomeTopTwentyLazyRow(
                modifier = modifier,
                homeTopTwentyDataList = homeTopTwentyDataList
            )
        }

        item {
            Text(
                text = stringResource(R.string.text_home_current_contents),
                color = MaterialTheme.colorScheme.secondary,
                modifier = modifier
                    .padding(horizontal = 16.dp),
                fontSize = 24.sp
            )

            HomeCurrentContentsLazyRow(
                modifier = modifier,
                homeCurrentContentsDataList = homeCurrentContentsDataList
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val viewmodel = hiltViewModel<HomeViewModel>()
    ATSOPTANDROIDTheme {
        HomeScreen(
            modifier = Modifier,
            navController = navController,
            homeViewModel = viewmodel
        )
    }
}