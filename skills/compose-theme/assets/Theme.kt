package theme.template.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.compose.ripple.rememberRippleIndication
import com.composables.googlefonts.rememberGoogleFontFamily

// Neutral starter values. Adapt to the design and remove unused roles.
@Immutable
data class ThemeColors(
    val background: Color = Color.White,
    val onBackground: Color = Color(0xFF171717),
    val surface: Color = Color(0xFFF5F5F5),
    val onSurface: Color = Color(0xFF171717),
    val primary: Color = Color(0xFF171717),
    val onPrimary: Color = Color.White,
    val onBackgroundMuted: Color = Color(0xFF525252),
    val textSelection: Color = Color(0x33171717),
    val border: Color = Color(0xFFD4D4D4),
)

@Immutable
data class ThemeTypography(
    val fontFamily: FontFamily = FontFamily.SansSerif,
    val display: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 46.sp, lineHeight = 52.sp, fontWeight = FontWeight.Bold),
    val headline: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 32.sp, lineHeight = 38.sp, fontWeight = FontWeight.Bold),
    val title: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold),
    val body: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.Normal),
    val label: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium),
    val caption: TextStyle = TextStyle(fontFamily = fontFamily, fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Normal),
)

@Immutable
data class ThemeShapes(
    val small: Shape = RoundedCornerShape(4.dp),
    val medium: Shape = RoundedCornerShape(8.dp),
    val large: Shape = RoundedCornerShape(16.dp),
    val fullyRounded: Shape = RoundedCornerShape(50),
)

private val LocalColors = staticCompositionLocalOf<ThemeColors> { error("Theme is missing") }
private val LocalTypography = staticCompositionLocalOf<ThemeTypography> { error("Theme is missing") }
private val LocalShapes = staticCompositionLocalOf<ThemeShapes> { error("Theme is missing") }
private val LocalSpace = staticCompositionLocalOf { 4.dp }

val LocalContentColor = compositionLocalOf<Color> { error("Theme is missing") }
val LocalTextStyle = compositionLocalOf<TextStyle> { error("Theme is missing") }

object Theme {
    val colors: ThemeColors
        @Composable get() = LocalColors.current
    val typography: ThemeTypography
        @Composable get() = LocalTypography.current
    val shapes: ThemeShapes
        @Composable get() = LocalShapes.current
}

val Space: Dp
    @Composable get() = LocalSpace.current

@Composable
fun rememberThemeTypography(
    familyName: String? = null,
    weights: List<Int> = listOf(400, 500, 700),
    fallback: FontFamily = FontFamily.SansSerif,
): ThemeTypography {
    val family = if (familyName == null) fallback else rememberGoogleFontFamily(
        family = familyName,
        weights = weights,
        fallback = fallback,
    )
    return remember(family) { ThemeTypography(fontFamily = family) }
}

@Composable
fun Theme(
    colors: ThemeColors = ThemeColors(),
    typography: ThemeTypography = rememberThemeTypography(),
    space: Dp = 4.dp,
    shapes: ThemeShapes = ThemeShapes(),
    contentColor: Color = colors.onBackground,
    textStyle: TextStyle = typography.body,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography,
        LocalSpace provides space,
        LocalShapes provides shapes,
        LocalContentColor provides contentColor,
        LocalTextStyle provides textStyle,
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = colors.primary,
            backgroundColor = colors.textSelection,
        ),
        LocalIndication provides rememberRippleIndication(color = contentColor),
        content = content,
    )
}

@Composable
fun ProvideContentColor(color: Color, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalContentColor provides color,
        LocalIndication provides rememberRippleIndication(color = color),
        content = content,
    )
}

@Composable
fun ProvideTextStyle(style: TextStyle, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.merge(style),
        content = content,
    )
}
