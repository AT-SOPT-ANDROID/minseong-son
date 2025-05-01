package org.sopt.at.views.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.sopt.at.extensions.NoRippleInteractionSource
import org.sopt.at.views.navigation.BottomNavItems
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val items = BottomNavItems.items
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = MyAtSoptTheme.colors.white
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            //해당 목적지의 상태를 저장
                            saveState = true
                        }
                        //중복 방지 - 스택
                        launchSingleTop = true
                        //이전 저장 상태 복원할 때 사영
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = stringResource(item.title),
                        modifier = modifier.wrapContentSize(),
                        tint = if(isSystemInDarkTheme()) {
                            Color.Unspecified
                        } else {
                            MyAtSoptTheme.colors.black
                        }
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = MyAtSoptTheme.typography.button,
                        color = MyAtSoptTheme.colors.black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MyAtSoptTheme.colors.black,
                    selectedTextColor = MyAtSoptTheme.colors.black,
                    unselectedIconColor = MyAtSoptTheme.colors.black,
                    indicatorColor = Color.Transparent
                ),
                interactionSource = NoRippleInteractionSource,
            )
        }
    }
}
