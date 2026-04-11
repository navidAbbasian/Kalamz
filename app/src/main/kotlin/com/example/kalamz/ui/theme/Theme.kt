package com.example.kalamz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KalamzColorScheme = lightColorScheme(
    primary = RedPrimary,
    onPrimary = White,
    primaryContainer = RedMid,
    onPrimaryContainer = White,
    secondary = GoldAccent,
    onSecondary = DarkText,
    secondaryContainer = Color(0xFFFFF9C4),
    onSecondaryContainer = DarkText,
    tertiary = GreenAccent,
    onTertiary = White,
    background = RedDark,
    onBackground = White,
    surface = RedMid,
    onSurface = White,
    surfaceVariant = GlassWhiteMid,
    onSurfaceVariant = White,
    error = Color(0xFFFF5252),
    onError = White,
    outline = GlassBorder
)

@Composable
fun KalamzTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KalamzColorScheme,
        typography = KalamzTypography,
        content = content
    )
}
