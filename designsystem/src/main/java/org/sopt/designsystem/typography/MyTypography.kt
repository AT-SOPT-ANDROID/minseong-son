package org.sopt.designsystem.typography

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class MyTypography(
    val title : TextStyle,
    val subtitle : TextStyle,
    val body : TextStyle,
    val button : TextStyle,
    val caption : TextStyle
)
