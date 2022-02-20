package com.nikodem.adoptme.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Black = Color.Black
val White = Color.White
val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d3c)
val Red800 = Color(0xffd00036)
val Red900 = Color(0xffc20029)

val BlackOpacity50 = Color(0xFF000000).copy(alpha = 0.5f)

@get:Composable
val Colors.BackgroundColor: Color
    get() = if (isLight) Red900 else Red900
