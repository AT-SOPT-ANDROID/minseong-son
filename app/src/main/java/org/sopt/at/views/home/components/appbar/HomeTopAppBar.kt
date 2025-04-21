package org.sopt.at.views.home.components.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.sopt.at.R
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.views.navigation.Route

@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    navController : NavController,
) {
    Row (
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

        IconButton(onClick = {
            navController.navigate(Route.PROFILE) {
                launchSingleTop = true
            }
        }) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.profile)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTopAppBarPreview() {
    val modifier = Modifier
    val navController = rememberNavController()
    ATSOPTANDROIDTheme {
        HomeTopAppBar(
            navController = navController,
            modifier = modifier,
        )
    }
}