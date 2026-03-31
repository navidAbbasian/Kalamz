package com.example.kalamz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KalamzColorScheme = lightColorScheme(
    primary = RedPrimary,
    onPrimary = White,
    primaryContainer = RedLight,
    onPrimaryContainer = RedDark,
    secondary = YellowAccent,
    onSecondary = DarkText,
    secondaryContainer = Color(0xFFFFF9C4),
    onSecondaryContainer = DarkText,
    tertiary = GreenAccent,
    onTertiary = White,
    background = White,
    onBackground = DarkText,
    surface = White,
    onSurface = DarkText,
    surfaceVariant = RedSurface,
    onSurfaceVariant = DarkText,
    error = Color(0xFFD32F2F),
    onError = White,
    outline = MediumGray
)

@Composable
fun KalamzTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KalamzColorScheme,
        typography = KalamzTypography,
        content = content
    )
}

