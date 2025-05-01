package org.sopt.at.components.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun StableImage (
    drawableResId: Int,
    contentDescription : String,
    modifier : Modifier = Modifier,
    contentScale : ContentScale = ContentScale.Fit
) {
    val painter = painterResource(id = drawableResId)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}