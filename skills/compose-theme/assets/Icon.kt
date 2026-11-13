package theme.template.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.vector.ImageVector
import theme.template.theme.LocalContentColor

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        colorFilter = ColorFilter.tint(if (tint.isSpecified) tint else LocalContentColor.current),
    )
}
