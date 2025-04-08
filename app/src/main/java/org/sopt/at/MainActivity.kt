package org.sopt.at

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

class MainActivity : ComponentActivity() {
    private var email : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        email = intent.getStringExtra("email")

        setContent {
            ATSOPTANDROIDTheme {
                val interactionSource = remember { MutableInteractionSource() }
                Scaffold(
                    topBar = {
                        Row (
                            modifier = Modifier.fillMaxWidth()
                                .padding(WindowInsets.statusBars.asPaddingValues()) //status bar 밑에 위치하도록
                        ){
                            IconButton(
                                onClick = {
                                    finish()
                                },
                                interactionSource = interactionSource,
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                    contentDescription = "뒤로가기"
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(
                                onClick = {
                                    Toast.makeText(this@MainActivity, "다음 업데이트를 기대해주세요.", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "알림"
                                )
                            }

                            IconButton(
                                onClick = {
                                    Toast.makeText(this@MainActivity, "다음 업데이트를 기대해주세요.", Toast.LENGTH_SHORT).show()
                                },
                                interactionSource = interactionSource
                            ) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "세팅"
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeScreen(
                        interactionSource = interactionSource,
                        profileEmail = email,
                        paddingValues = innerPadding
                    ) {
                        val intent = Intent(this@MainActivity, SignInActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    interactionSource : MutableInteractionSource,
    profileEmail : String?,
    paddingValues: PaddingValues,
    onLogOutClick : () -> Unit
) {
    Column (
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(R.drawable.sopt_android),
                contentDescription = "프로필 아이콘",
            )

            Text(
                text = "현재 이메일 : $profileEmail",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.weight(1f))


            Box(
                modifier = Modifier.wrapContentSize()
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Text(
                    text = "프로필 전환",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onLogOutClick()
            },
            //enabled = isValueCheck.split(" ").first().toBoolean(),
            shape = RectangleShape,
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                //.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            interactionSource = interactionSource
        ) {
            Text(text = "로그아웃", fontSize = 14.sp, color = Color.White)
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