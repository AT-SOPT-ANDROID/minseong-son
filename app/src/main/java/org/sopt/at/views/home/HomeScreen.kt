package org.sopt.at.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import org.sopt.at.models.home.HomeTabEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.viewmodels.HomeViewModel
import org.sopt.at.views.home.components.appbar.HomeTopAppBar
import org.sopt.at.views.home.components.banner.HomeBannerLazyRow
import org.sopt.at.views.home.components.currentcontents.HomeCurrentContentsLazyRow
import org.sopt.at.views.home.components.tab.HomeTabRow
import org.sopt.at.views.home.components.toptwenty.HomeTopTwentyLazyRow
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val listState = rememberLazyListState()

    val homeBannerDataList by homeViewModel.homeBannerDataList.collectAsState()

    //그냥 선언된 거 가져올 때 remember에 넣어서 불필요한 리컴포지션 방지
    val homeTopTwentyDataList = remember { homeViewModel.getHomeTopTwentyItemList() }
    val homeCurrentContentsDataList = remember { homeViewModel.getHomeCurrentContentsItemList() }

    val homeTab by homeViewModel.homeTab.collectAsState()
    val tabDataList by homeViewModel.tabDataList.collectAsState()

    val coroutineScope = rememberCoroutineScope()


    val pagerState = rememberPagerState(
        pageCount = { tabDataList.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    //remember로 래핑하여 HomeTabRow가 재구성되더라도 onTabSelected는 메모이즈된 동일 참조를 유지
    val onTabSelected: (Int, HomeTabEntity) -> Unit = remember(homeViewModel, pagerState) {
        { index, value ->
            coroutineScope.launch {
                homeViewModel.fetchHomeTab(value)
                pagerState.animateScrollToPage(index)
            }
        }
    }

    /*LaunchedEffect(Unit) {
        homeViewModel.fetchHomeTab(HomeTabEntity(tab = CommonConstants.EMPTY_STRING))
    }*/

    LaunchedEffect(homeTab) {
        listState.animateScrollToItem(0)
    }

    LazyColumn (
        modifier = modifier
            .background(MyAtSoptTheme.colors.white)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        item {
            HomeTopAppBar(
                navController = navController
            )
        }

        stickyHeader {
            HomeTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyAtSoptTheme.colors.white),
                tabDataList = tabDataList,
                pagerState = pagerState,
                onTabSelected = onTabSelected
            )
        }

        item {
            HomeBannerLazyRow(
                homeBannerDataList = homeBannerDataList
            )
        }

        item {
            Text(
                text = stringResource(R.string.text_home_top_20),
                color = MyAtSoptTheme.colors.black,
                fontSize = 24.sp
            )

            HomeTopTwentyLazyRow(
                homeTopTwentyDataList = homeTopTwentyDataList
            )
        }

        item {
            Text(
                text = stringResource(R.string.text_home_current_contents),
                color = MyAtSoptTheme.colors.black,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                fontSize = 24.sp
            )

            HomeCurrentContentsLazyRow(
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
            navController = navController,
            homeViewModel = viewmodel
        )
    }
}