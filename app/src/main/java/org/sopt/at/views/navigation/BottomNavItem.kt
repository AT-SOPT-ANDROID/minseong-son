package org.sopt.at.views.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.at.R

sealed class BottomNavItem(
    //세이프티 툴 - 컴파일러에게 명확히 의도를 전달
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screenRoute: String
) {
    data object Home : BottomNavItem(R.string.home, R.drawable.baseline_home_filled_24, Route.HOME)
    data object Shorts : BottomNavItem(R.string.shorts, R.drawable.shorts_icon, Route.SHORTS)
    data object Live : BottomNavItem(R.string.live, R.drawable.live_icon, Route.LIVE)
    data object Search : BottomNavItem(R.string.search, R.drawable.baseline_search_24, Route.SEARCH)
    data object History : BottomNavItem(R.string.history, R.drawable.history_icon, Route.HISTORY)
}