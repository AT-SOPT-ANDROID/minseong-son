package org.sopt.at.views.shorts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.sopt.at.views.navigation.Screen
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun ShortsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MyAtSoptTheme.colors.white),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = Screen.Shorts.route,
            fontWeight = FontWeight.Bold,
            color = MyAtSoptTheme.colors.black
        )
    }
}