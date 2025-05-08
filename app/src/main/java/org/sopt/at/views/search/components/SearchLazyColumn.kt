package org.sopt.at.views.search.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchLazyColumn(
    searchDataList : List<String>
) {
    LazyColumn {
        items(searchDataList.size) {
            SearchItem(
                item = searchDataList[it],
                onClick = {
                    //Todo: 나중에 구현 ㅎ
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchLazyColumnPreview() {
    SearchLazyColumn(
        searchDataList = listOf("Test1", "Test2")
    )
}