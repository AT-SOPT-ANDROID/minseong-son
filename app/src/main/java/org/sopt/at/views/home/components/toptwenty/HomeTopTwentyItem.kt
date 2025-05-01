package org.sopt.at.views.home.components.toptwenty

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.at.R
import org.sopt.at.components.item.AtSoptImageAndTitleComponents
import org.sopt.at.models.home.HomeTopAndCurrentEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.views.home.common.HomeItemTypeDescription

@Composable
fun HomeTopTwentyItem(
    item: HomeTopAndCurrentEntity,
    modifier : Modifier = Modifier
) {
    Row (
        modifier = modifier
            .width(LocalConfiguration.current.screenHeightDp.dp * 0.3f)
            .height(LocalConfiguration.current.screenHeightDp.dp * 0.4f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        //순위용 텍스트
        Text(
            text = item.rank.toString(),
            color = Color.White,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.Bottom),
            fontSize = with(LocalDensity.current) { (LocalConfiguration.current.screenHeightDp.dp * 0.1f) .toSp() },
            maxLines = 1,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
        )

        //이미지
        AtSoptImageAndTitleComponents(
            painter = painterResource(item.imageUrl?.toInt() ?: R.drawable.baseline_image_24),
            contentDescription = stringResource(R.string.home_banner_content_description),
            title = item.title,
            subtitle = item.subtitle,
            isNewData = item.isNewData,
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            heightRatio = 0.4f,
            item = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTopTwentyItemPreview() {
    val previewTestItem = HomeTopAndCurrentEntity(
        id = 0,
        imageUrl = "테스트 이미지",
        title = "테스트 타이틀",
        subtitle = HomeItemTypeDescription.TYPE_ONLY,
        rank = 1,
        isNewData = true
    )

    ATSOPTANDROIDTheme {
        HomeTopTwentyItem(
            modifier = Modifier,
            item = previewTestItem
        )
    }
}