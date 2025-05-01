package org.sopt.at.views.home.components.currentcontents

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.at.R
import org.sopt.at.components.item.AtSoptImageAndTitleComponents
import org.sopt.at.models.home.HomeTopAndCurrentEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.views.home.common.HomeItemTypeDescription

@Composable
fun HomeCurrentContentsItem(
    modifier: Modifier = Modifier,
    item: HomeTopAndCurrentEntity
) {
    AtSoptImageAndTitleComponents(
        drawableResId = item.imageUrl?.toInt() ?: R.drawable.baseline_image_24,
        contentDescription = stringResource(R.string.home_banner_content_description),
        title = item.title,
        subtitle = item.subtitle,
        isNewData = item.isNewData,
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        widthRatio = 0.4f,
        heightRatio = 0.3f,
        item = null
    )
}

@Preview(showBackground = true)
@Composable
fun HomeCurrentPreview() {
    val previewTestItem = HomeTopAndCurrentEntity(
        id = 0,
        imageUrl = "테스트 이미지",
        title = "테스트 타이틀",
        subtitle = HomeItemTypeDescription.TYPE_ORIGINAL,
        isNewData = true
    )

    ATSOPTANDROIDTheme {
        HomeCurrentContentsItem(
            modifier = Modifier,
            item = previewTestItem
        )
    }
}