package org.sopt.at.views.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.at.R

sealed class BottomNavItem(
    //세이프티 툴 - 컴파일러에게 명확히 의도를 전달
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screen: Screen
) {
    data object Home : BottomNavItem(R.string.home, R.drawable.baseline_home_filled_24, Screen.Home)
    data object Shorts : BottomNavItem(R.string.shorts, R.drawable.shorts_icon, Screen.Shorts)
    data object Live : BottomNavItem(R.string.live, R.drawable.live_icon, Screen.Live)
    data object Search : BottomNavItem(R.string.search, R.drawable.baseline_search_24, Screen.Search)
    data object History : BottomNavItem(R.string.history, R.drawable.history_icon, Screen.History)
}