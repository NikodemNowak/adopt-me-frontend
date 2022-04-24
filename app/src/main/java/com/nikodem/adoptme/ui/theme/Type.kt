package com.nikodem.adoptme.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nikodem.adoptme.R

//private val Montserrat = FontFamily(
//    Font(R.font.montserrat_regular),
//    Font(R.font.montserrat_medium, FontWeight.W500),
//    Font(R.font.montserrat_semibold, FontWeight.W600)
//)
//
//private val Domine = FontFamily(
//    Font(R.font.domine_regular),
//    Font(R.font.domine_bold, FontWeight.Bold)
//)

val JetnewsTypography = Typography(
//    defaultFontFamily = Montserrat,
    h4 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
)

@get:Composable
val Typography.mySuperFontStyle: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 56.sp,
        letterSpacing = (-0.1).sp
    )

@get:Composable
val Typography.BO50FS15: TextStyle
    get() = TextStyle(
        fontSize = 15.sp,
        letterSpacing = (-0.1).sp,
        color = BlackOpacity50
    )

@get:Composable
val Typography.BO50FS10: TextStyle
    get() = TextStyle(
        fontSize = 10.sp,
        letterSpacing = (-0.1).sp,
        color = BlackOpacity50
    )

@get:Composable
val Typography.BFS25BOLD: TextStyle
    get() = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        letterSpacing = (-0.1).sp,
        color = Black
    )

@get:Composable
val Typography.BFS20: TextStyle
    get() = TextStyle(
        fontSize = 20.sp,
        letterSpacing = (-0.1).sp,
        color = Black
    )

@get:Composable
val Typography.RFS20: TextStyle
    get() = TextStyle(
        fontSize = 20.sp,
        letterSpacing = (-0.1).sp,
        color = Red
    )