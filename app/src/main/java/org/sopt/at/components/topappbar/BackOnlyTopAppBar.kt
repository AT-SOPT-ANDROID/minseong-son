package org.sopt.at.components.topappbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.sopt.at.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackOnlyTopAppBar(
    modifier: Modifier = Modifier,
    navController : NavController,
) {
    TopAppBar(
        modifier = modifier
            .wrapContentSize(),
        title = {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .wrapContentSize()
                            .align(Alignment.Top),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.btn_back)
                        )
                    }
                }
            }
        }
    )
}