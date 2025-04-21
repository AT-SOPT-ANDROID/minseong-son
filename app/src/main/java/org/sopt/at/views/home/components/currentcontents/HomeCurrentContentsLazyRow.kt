package org.sopt.at.views.home.components.currentcontents

import androidx.compose.foundation.layout.Arrangement
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
fun HomeCurrentContentsLazyRow(
    modifier: Modifier = Modifier,
    homeCurrentContentsDataList: List<HomeTopAndCurrentEntity>
) {
    LazyRow (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            items = homeCurrentContentsDataList
        ) { _, item ->
            HomeCurrentContentsItem(
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
        org.sopt.at.views.home.components.toptwenty.HomeTopTwentyLazyRow(
            modifier = Modifier,
            homeTopTwentyDataList = dummyList
        )
    }
}