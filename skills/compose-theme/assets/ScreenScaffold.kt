package theme.template.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import com.composables.systembars.SystemBarContent
import com.composables.systembars.SystemBars
import theme.template.theme.Theme
import theme.template.theme.LocalContentColor

@Composable
fun ScreenScaffold(
    modifier: Modifier = Modifier,
    statusBarIcons: SystemBarContent = if (Theme.colors.background.luminance() > 0.5f) SystemBarContent.Dark else SystemBarContent.Light,
    navigationBarIcons: SystemBarContent = statusBarIcons,
    content: @Composable BoxScope.() -> Unit,
) {
    SystemBars(statusBar = statusBarIcons, navigationBar = navigationBarIcons)
    CompositionLocalProvider(LocalContentColor provides Theme.colors.onBackground) {
        Box(
            modifier = modifier.fillMaxSize().background(Theme.colors.background),
            content = content,
        )
    }
}
