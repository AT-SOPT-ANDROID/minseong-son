package org.sopt.at.views.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.viewmodels.HomeViewModel
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.history.HistoryScreen
import org.sopt.at.views.home.HomeScreen
import org.sopt.at.views.live.LiveScreen
import org.sopt.at.views.my.ProfileScreen
import org.sopt.at.views.navigation.Route
import org.sopt.at.views.search.SearchScreen
import org.sopt.at.views.shorts.ShortsScreen
import org.sopt.at.views.signin.SignInScreen
import org.sopt.at.views.signup.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    viewModel: HomeViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val isLoggedIn by signInViewModel.isLoggedIn.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn == true) Route.HOME else Route.SIGN_IN
    ) {
        //로그인
        composable(Route.SIGN_IN) {
            SignInScreen(
                navController = navController,
                modifier = modifier
            )
        }

        //회원가입
        composable(Route.SIGN_UP) {
            SignUpScreen(
                navController = navController,
                modifier = modifier
            )
        }

        //navigation 용
        composable(Route.HOME) {
            HomeScreen(
                navController = navController,
                modifier = modifier,

            )
        }

        composable(Route.SHORTS) {
            ShortsScreen(
                modifier = modifier
            )
        }

        composable(Route.LIVE) {
            LiveScreen(
                modifier = modifier
            )
        }

        composable(Route.SEARCH) {
            SearchScreen(
                modifier = modifier
            )
        }

        composable(Route.HISTORY) {
            HistoryScreen(
                navController = navController,
                modifier = modifier,
                viewModel = historyViewModel,
            )
        }

        composable(Route.PROFILE) {
            ProfileScreen(
                navController = navController,
                modifier = modifier
            )
        }
    }
}