package org.sopt.designsystem.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.sopt.designsystem.colors.MyColors
import org.sopt.designsystem.typography.MyTypography

private val DarkMyColor = MyColors(
    buttonGray = DarkButtonGrayColor,
    dialogContainer = DarkDialogContainerColor,
    gray1 = DarkGray1,
    gray2 = DarkGray2,
    gray3 = DarkGray3,
    gray4 = DarkGray4,
    gray5 = DarkGray5,
    black = DarkBasicBlack,
    white = DarkBasicWhite,
    brand = DarkBrand,
)

private val LightMyColor = MyColors(
    buttonGray = ButtonGrayColor,
    dialogContainer = DialogContainerColor,
    gray1 = Gray1,
    gray2 = Gray2,
    gray3 = Gray3,
    gray4 = Gray4,
    gray5 = Gray5,
    black = BasicBlack,
    white = BasicWhite,
    brand = Brand,
)

val defaultMyTypography = MyTypography(
    title = TextStyle(
        fontFamily = atSoptFontBold,
        fontSize = 24.sp,
        fontWeight = FontWeight(700),
        lineHeight = 32.sp,
    ),

    subtitle = TextStyle(
        fontFamily = atSoptFontSemiBold,
        fontSize = 18.sp,
        fontWeight = FontWeight(600),
        lineHeight = 26.sp,
    ),

    body = TextStyle(
        fontFamily = atSoptFontRegular,
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        lineHeight = 24.sp,
    ),

    button = TextStyle(
        fontFamily = atSoptFontBold,
        fontSize = 14.sp,
        fontWeight = FontWeight(700),
        lineHeight = 20.sp,
    ),

    caption = TextStyle(
        fontFamily = atSoptFontRegular,
        fontSize = 12.sp,
        fontWeight = FontWeight(400),
        lineHeight = 16.sp,
    )
)

val LocalMyColorsProvider = staticCompositionLocalOf<MyColors> {
    error("No MyColors provided")
}

val LocalMyTypographyProvider = staticCompositionLocalOf {
    defaultMyTypography
}

//앱 어디서든 색상과 타이포그래피에 접근할 수 있도록 공식적인 진입점 역할을 합니다.
//@Composable 그리고 @ReadOnlyComposable이 붙은 이유는 컴포지션 중에만 사용되며 상태를 구독하지 않고 읽기 전용으로 사용하기 위함
object MyAtSoptTheme {
    //Backend Internal error: Exception during IR lowering 발생
    /*@Composable
    @ReadOnlyComposable
    fun colors(): MyColors = LocalMyColorsProvider.current*/
    val colors: MyColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMyColorsProvider.current

   /* @Composable
    @ReadOnlyComposable
    fun typography(): MyTypography = LocalMyTypographyProvider.current*/
    val typography: MyTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMyTypographyProvider.current
}

@Composable
fun ProvideMyColorsAndTypography(
    colors: MyColors,
    typography: MyTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMyColorsProvider provides colors,
        LocalMyTypographyProvider provides typography,
        content = content
    )
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AtSpotANDROIDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkMyColor else LightMyColor

    ProvideMyColorsAndTypography(
        colors = colors,
        typography = defaultMyTypography,
    ) {
        /*val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = !darkTheme
                }
            }
        }*/
        MaterialTheme(
            content = content
        )
    }
}


/*
@Composable
fun MyAtSoptTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkMyColorScheme
        else -> LightMyColorScheme
    }

    val myColors = if (darkTheme) DarkMyColorScheme else LightMyColorScheme

    CompositionLocalProvider(
        LocalMyColorsProvider provides myColors,
        LocalWithSuhyeonTypographyProvider provides WithSuhyeonTypography // ← 필요한 경우
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}*/
