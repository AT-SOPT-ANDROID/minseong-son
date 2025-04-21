package org.sopt.at.views.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.at.R
import org.sopt.at.components.item.AtSoptImageAndTitleComponents
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.models.history.HistoryEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

@Composable
fun HistoryLazyGrid(
    modifier : Modifier = Modifier,
    historyList : List<HistoryEntity>,
    onLongClick : (HistoryEntity?) -> Unit? = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(historyList) { item ->
            AtSoptImageAndTitleComponents(
                painter = painterResource(R.drawable.series_recruit3),
                contentDescription = stringResource(R.string.home_banner_content_description),
                item = item,
                title = item.title ?: CommonConstants.EMPTY_STRING,
                subtitle = item.type,
                isNewData = false,
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
                widthRatio = 0.4f,
                heightRatio = 0.3f,
            ) {
                onLongClick(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryLazyGridPreview() {
    ATSOPTANDROIDTheme {
        HistoryLazyGrid(
            historyList = listOf(
                HistoryEntity(
                    id = 0,
                    imageUrl = "",
                    title = "title",
                    type = "type"
                )
            )
        )
    }
}