package org.sopt.at.components.item

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.sopt.at.R
import org.sopt.at.components.image.StableImage
import org.sopt.at.models.history.HistoryEntity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun AtSoptImageAndTitleComponents(
    drawableResId: Int,
    contentDescription: String,
    item : HistoryEntity?,
    title: String,
    subtitle: String? = null,
    isNewData : Boolean? = null,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    widthRatio : Float? = null,
    heightRatio: Float = 0.4f,
    cornerRadius: Dp = 16.dp,
    onLongClick : (HistoryEntity?) -> Unit? = {}
) {
    //configuration 대신 windowinfo 사용 => compose에서 정한 dp값 대신 실제 기기 크기를 가져옴
    val density = LocalDensity.current
    val windowSizePx = LocalWindowInfo.current.containerSize
    val windowSizeDp = with(density) { DpSize(windowSizePx.width.toDp(), windowSizePx.height.toDp()) }

    val boxWidth = widthRatio?.let { windowSizeDp.width * it } ?: windowSizeDp.width
    val boxHeight = windowSizeDp.height * heightRatio

    Box(
        modifier = modifier
            .animateContentSize()
            .width(boxWidth)
            .height(boxHeight)
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .combinedClickable(
                onClick = {
                },
                onLongClick = {
                    onLongClick(item)
                }
            )
    ) {
        if (isNewData != null && isNewData) {
            Text (
                text =  stringResource(R.string.text_home_top_twenty_new),
                color = MyAtSoptTheme.colors.white,
                modifier = modifier
                    .padding(top = 4.dp, start = 8.dp)
                    .background(MyAtSoptTheme.colors.brand, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp)
                    .zIndex(1f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        StableImage(
            drawableResId = drawableResId,
            contentDescription = contentDescription,
            modifier = modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MyAtSoptTheme.colors.black.copy(alpha = 0.6f))
                    )
                )
                .height(boxHeight * 0.2f)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = horizontalAlignment
        ) {
            Text(
                text = title,
                color = MyAtSoptTheme.colors.white,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 16.dp),
                fontSize = 24.sp,
            )

            if (!subtitle.isNullOrEmpty() && isNewData != null) {
                Row (
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StableImage(
                        drawableResId = R.drawable.sopt_android,
                        contentDescription = stringResource(R.string.text_home_item_only),
                        modifier = modifier
                            .size(20.dp)
                    )

                    Text(
                        text = subtitle,
                        color = MyAtSoptTheme.colors.white,
                        modifier = modifier,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else if (subtitle != null) {
                Text(
                    text = subtitle,
                    color = MyAtSoptTheme.colors.white,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeComponentsPreview() {
    val testItem = HistoryEntity(
        id = 0,
        imageUrl = "",
        title = "title",
        type = "type"
    )

    ATSOPTANDROIDTheme {
        AtSoptImageAndTitleComponents(
            drawableResId = R.drawable.ani_giant,
            contentDescription = stringResource(R.string.home_banner_content_description),
            title = "테스트 타이틀",
            subtitle = "테스트 서브타이틀",
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            heightRatio = 0.4f,
            cornerRadius = 16.dp,
            item = testItem,
        ) {

        }
    }
}