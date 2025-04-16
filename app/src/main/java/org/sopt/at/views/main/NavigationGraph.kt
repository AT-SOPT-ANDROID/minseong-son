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
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.home.HomeScreen
import org.sopt.at.views.my.ProfileScreen
import org.sopt.at.views.navigation.Route
import org.sopt.at.views.signin.SignInScreen
import org.sopt.at.views.signup.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel()
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
                modifier = modifier
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