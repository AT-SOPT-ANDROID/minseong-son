package org.sopt.at.views.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.history.HistoryScreen
import org.sopt.at.views.home.HomeScreen
import org.sopt.at.views.live.LiveScreen
import org.sopt.at.views.my.ProfileScreen
import org.sopt.at.views.navigation.Screen
import org.sopt.at.views.search.SearchScreen
import org.sopt.at.views.shorts.ShortsScreen
import org.sopt.at.views.signin.SignInScreen
import org.sopt.at.views.signup.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val isLoggedIn by signInViewModel.isLoggedIn.collectAsState(initial = false)
    val destination = if (isLoggedIn == true) Screen.Home.route else Screen.SignIn.route

    LaunchedEffect(isLoggedIn) {

        navController.navigate(destination) {
            popUpTo(destination) {
                inclusive = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        //로그인
        composable(Screen.SignIn.route) {
            SignInScreen(
                navController = navController,
                modifier = modifier
            )
        }

        //회원가입
        composable(Screen.SignUp.route) {
            SignUpScreen(
                navController = navController,
                modifier = modifier
            )
        }

        //navigation 용
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(Screen.Shorts.route) {
            ShortsScreen()
        }

        composable(Screen.Live.route) {
            LiveScreen()
        }

        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable(Screen.History.route) {
            HistoryScreen(
                navController = navController,
                viewModel = historyViewModel,
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                modifier = modifier
            )
        }
    }
}