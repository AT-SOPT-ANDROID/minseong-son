package org.sopt.at.views.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.at.components.topappbar.BackOnlyTopAppBar
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.views.navigation.Route

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
                navController = navController,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ATSOPTANDROIDTheme(dynamicColor = false) {
        MainContent()
    }
}