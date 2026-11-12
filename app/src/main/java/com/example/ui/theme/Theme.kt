package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = IosBlueDark,
    onPrimary = IosDarkBackground,
    primaryContainer = IosDarkSecondarySurface,
    onPrimaryContainer = IosBlueDark,
    secondary = IosIndigo,
    onSecondary = IosDarkTextPrimary,
    background = IosDarkBackground,
    onBackground = IosDarkTextPrimary,
    surface = IosDarkSurface,
    onSurface = IosDarkTextPrimary,
    surfaceVariant = IosDarkSecondarySurface,
    onSurfaceVariant = IosDarkTextSecondary,
    outline = IosDarkDivider,
    outlineVariant = IosDarkTertiarySurface,
)

private val LightColorScheme = lightColorScheme(
    primary = IosBlue,
    onPrimary = IosLightSurface,
    primaryContainer = IosLightSecondarySurface,
    onPrimaryContainer = IosBlue,
    secondary = IosIndigo,
    onSecondary = IosLightTextPrimary,
    background = IosLightBackground,
    onBackground = IosLightTextPrimary,
    surface = IosLightSurface,
    onSurface = IosLightTextPrimary,
    surfaceVariant = IosLightSecondarySurface,
    onSurfaceVariant = IosLightTextSecondary,
    outline = IosLightDivider,
    outlineVariant = IosLightTertiarySurface,
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
