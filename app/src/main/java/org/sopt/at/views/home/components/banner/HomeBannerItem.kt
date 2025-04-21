package org.sopt.at.views.home.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.at.R
import org.sopt.at.components.item.AtSoptImageAndTitleComponents
import org.sopt.at.models.home.HomeBannerEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

@Composable
fun HomeBannerItem(
    item: HomeBannerEntity,
    modifier : Modifier = Modifier
) {
    AtSoptImageAndTitleComponents(
        painter = painterResource(item.imageUrl?.toInt() ?: R.drawable.baseline_image_24),
        contentDescription = stringResource(R.string.home_banner_content_description),
        title = item.title.toString(),
        subtitle = item.subtitle.toString(),
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        widthRatio = 0.9f,
        heightRatio = 0.6f,
        item = null
    )
}

@Preview(showBackground = true)
@Composable
fun HomeItemPreview() {
    val previewTestItem = HomeBannerEntity(
        id = 0,
        imageUrl = "테스트 이미지",
        title = "테스트 타이틀",
        subtitle = "테스트 서브타이틀",
    )

    ATSOPTANDROIDTheme {
        HomeBannerItem(
            modifier = Modifier,
            item = previewTestItem
        )
    }
}