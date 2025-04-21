package org.sopt.at.views.home.components.banner

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.at.models.home.HomeBannerEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.viewmodels.HomeViewModel


@Composable
fun HomeBannerLazyRow(
    modifier: Modifier = Modifier,
    homeBannerDataList: List<HomeBannerEntity>
) {
    val listState = rememberLazyListState()

    LazyRow (
        state = listState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    ) {
        itemsIndexed(
            items = homeBannerDataList
        ) { _, item ->
            HomeBannerItem(
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
    val dummyList by viewmodel.homeBannerDataList.collectAsState(initial = emptyList())
    ATSOPTANDROIDTheme {
        HomeBannerLazyRow(
            modifier = Modifier,
            homeBannerDataList = dummyList
        )
    }
}