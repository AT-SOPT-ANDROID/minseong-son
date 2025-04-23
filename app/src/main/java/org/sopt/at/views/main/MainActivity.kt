package org.sopt.at.views.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.at.R
import org.sopt.at.components.dialogs.DialogType
import org.sopt.at.components.dialogs.ExitDialog
import org.sopt.at.views.history.components.tab.HistoryTabData
import org.sopt.at.components.topappbar.BackOnlyTopAppBar
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.viewmodels.HistoryViewModel
import org.sopt.at.viewmodels.MainViewModel
import org.sopt.at.views.navigation.Screen

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
                    ) {
                        finish()
                    }
                }
            }
        }
    }
}


@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    viewModel : MainViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
    onConfirm : () -> Unit = {},
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val historyTab by historyViewModel.historyTab.collectAsState()

    val dialogState by viewModel.dialogState.collectAsState()

    BackHandler {
        if (!dialogState) {
            viewModel.openDialog()
        }
    }

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(
                Screen.SignUp.route,
                Screen.SignIn.route
                )
            ) {
                MainBottomNavigation(
                    modifier = Modifier,
                    navController = navController
                )
            }
        },

        topBar = {
            if (currentRoute in listOf(
                    Screen.SignUp.route,
                    Screen.SignIn.route
                )
            ) {
                BackOnlyTopAppBar(
                    modifier = Modifier,
                    navController = navController
                )
            }
        },

        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            if (currentRoute == Screen.History.route && historyTab == HistoryTabData.TAB_SERIES) {
                FloatingActionButton(
                    onClick = {
                        historyViewModel.openHistoryDialog(DialogType.DIALOG_TYPE_CREATE)
                    },
                    modifier = Modifier.padding(end = 10.dp),
                    containerColor = Color.Black,
                    contentColor = Color.White
                ) {
                    Icon(
                        Icons.Default.Create,
                        contentDescription = stringResource(R.string.floating_btn_create_content_description)
                    )
                }
            }
        }
    ) {
        Box(modifier.padding(it)) {
            NavigationGraph(
                navController = navController,
                modifier = Modifier,
                historyViewModel = historyViewModel,
            )
        }


        if (dialogState) {
            ExitDialog(
                onConfirm = {
                    onConfirm()
                },
                onDismiss = {
                    viewModel.closeDialog()
                }
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