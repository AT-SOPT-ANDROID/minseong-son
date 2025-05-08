package org.sopt.at.views.my

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.sopt.at.R
import org.sopt.at.components.dialogs.AtSoptDialog
import org.sopt.at.components.image.StableImage
import org.sopt.at.core.common.CommonConstants
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme
import org.sopt.at.utils.PreferenceDataStore
import org.sopt.at.viewmodels.ProfileViewModel
import org.sopt.at.viewmodels.SignInViewModel
import org.sopt.at.views.navigation.Screen
import org.sopt.at.views.signin.LoginResult
import org.sopt.at.views.signin.SignInEvent
import org.sopt.designsystem.theme.MyAtSoptTheme

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    signInViewModel: SignInViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val loginState by signInViewModel.signInState.collectAsState()
    val dialogState by profileViewModel.nicknameDialogState.collectAsState()

    val context = LocalContext.current

    val currentNickname by profileViewModel.profileNickname.collectAsState()
    val currentUserId by PreferenceDataStore.getUserId(context = context).collectAsState(initial = CommonConstants.EMPTY_LONG)

    if (currentUserId != null && currentUserId != CommonConstants.EMPTY_LONG) {
        LaunchedEffect(Unit) {
            profileViewModel.getProfileNickname(currentUserId!!)
        }
    }

    LaunchedEffect(loginState.loginResult) {
        if (loginState.loginResult == LoginResult.LogOut) {
            navController.navigate(Screen.SignIn.route) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .background(MyAtSoptTheme.colors.white)
    ) {
        Row (
            modifier = modifier.fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StableImage(
                drawableResId = R.drawable.sopt_android,
                contentDescription = stringResource(R.string.profile_icon_content_description)
            )

            if (!currentNickname.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.current_email, currentNickname!!),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            } else {
                Text(
                    text = stringResource(R.string.profile_empty_nickname),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }

            Spacer(modifier = modifier.width(5.dp))

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = CommonConstants.EMPTY_STRING,
                modifier = modifier
                    .clickable {
                        profileViewModel.openDialog(Screen.Profile.toString())
                    }
            )

            Spacer(modifier = modifier.weight(1f))


            Box(
                modifier = modifier.wrapContentSize()
                    .border(
                        width = 1.dp,
                        color = MyAtSoptTheme.colors.white,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Text(
                    text = stringResource(R.string.profile_change_content_description),
                    fontSize = 12.sp,
                    modifier = modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = modifier.weight(1f))

        Text(
            text = stringResource(R.string.btn_sign_out),
            fontSize = 14.sp,
            color = MyAtSoptTheme.colors.white,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, MyAtSoptTheme.colors.white, RectangleShape)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    signInViewModel.onEvent(SignInEvent.LogOutClicked)
                },
            textAlign = TextAlign.Center
        )
    }

    if (dialogState.isVisible) {
        AtSoptDialog(
            screen = Screen.Profile,
            text = currentNickname ?: CommonConstants.EMPTY_STRING,

            onTextChanged = {
                profileViewModel.onTextChangedProfileNickname(it.trim())
            },

            onConfirm = {
                profileViewModel.patchNickname(currentUserId!!, it)
                profileViewModel.closeDialog()
            },

            onDismiss = {
                profileViewModel.closeDialog()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    ATSOPTANDROIDTheme {
        ProfileScreen(navController)
    }
}