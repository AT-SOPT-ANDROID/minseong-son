package org.sopt.at.views.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sopt.at.R
import org.sopt.at.components.topappbar.BackOnlyTopAppBar
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.extensions.NoRippleInteractionSource
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.home.HomeScreen
import org.sopt.at.views.my.ProfileScreen
import org.sopt.at.views.navigation.BottomNavItem
import org.sopt.at.views.navigation.BottomNavItems
import org.sopt.at.views.navigation.Route
import org.sopt.at.views.signin.SignInScreen
import org.sopt.at.views.signup.SignUpScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ATSOPTANDROIDTheme {
                Surface {
                    MainContent(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(
                Route.SIGN_IN,
                Route.SIGN_UP
                )
            ) {
                MainBottomNavigation(
                    modifier = Modifier,
                    navController = navController
                )
            }
        },

        topBar = {
            if (currentRoute in listOf(Route.SIGN_IN, Route.SIGN_UP)) {
                BackOnlyTopAppBar(
                    modifier = Modifier,
                    navController = navController
                )
            } else {
                MainTopAppBar(
                    modifier = Modifier,
                    navController = navController
                )
            }
        }

    ) {
        Box(modifier.padding(it)) {
            NavigationGraph(
                context = context,
                navController = navController,
                modifier = Modifier
            )
        }
    }
}

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
        containerColor = Color.Transparent
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
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
                        tint = Color.Unspecified
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Blue,
                    selectedTextColor = Color.Blue,
                    indicatorColor = Color.White
                ),
                interactionSource = NoRippleInteractionSource,
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    navController : NavController,
) {
    TopAppBar(
        modifier = modifier
            .wrapContentSize(),
        title = {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.app_name_tving),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = modifier.padding(start = 5.dp),
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                    navController.navigate(Route.SEARCH) {
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.search)
                )
            }
        }
    )
}

@Composable
fun NavigationGraph(
    context: Context,
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

        /*composable("login") {
            LoginScreen(navController)
        }
        composable("search") {
            SearchScreen(navController)
        }
    }
}

//리플 제거 확장함수
fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = this.clickable(
    enabled = enabled,
    interactionSource = null,
    indication = null,
    onClick = onClick
)