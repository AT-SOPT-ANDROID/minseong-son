package org.sopt.at.views.navigation

//기존
/*
object Route {
    const val HOME = "home"
    const val SHORTS = "shorts"
    const val LIVE = "live"
    const val SEARCH = "search"
    const val HISTORY = "history"
    const val PROFILE = "profile"

    const val SIGN_IN = "sign_in"
    const val SIGN_UP = "sign_up"
}*/

//type safety 적용
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Shorts : Screen("shorts")
    data object Live : Screen("live")
    data object Search : Screen("search")
    data object History : Screen("history")
    data object Profile : Screen("profile")

    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
}
