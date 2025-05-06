package org.sopt.at.views.home.components.toptwenty

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.at.models.home.HomeTopAndCurrentEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.viewmodels.HomeViewModel

@Composable
fun HomeTopTwentyLazyRow(
    modifier: Modifier = Modifier,
    homeTopTwentyDataList: List<HomeTopAndCurrentEntity>
) {
    LazyRow (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            items = homeTopTwentyDataList
        ) { _, item ->
            HomeTopTwentyItem(
                item = item,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBannerLazyRowPreview() {
    val viewmodel = hiltViewModel<HomeViewModel>()
    val dummyList = viewmodel.getHomeTopTwentyItemList()
    ATSOPTANDROIDTheme {
        HomeTopTwentyLazyRow(
            modifier = Modifier,
            homeTopTwentyDataList = dummyList
        )
    }
}