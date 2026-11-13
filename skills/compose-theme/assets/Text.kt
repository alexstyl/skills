package theme.template.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import theme.template.theme.LocalContentColor
import theme.template.theme.LocalTextStyle

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    val mergedStyle = LocalTextStyle.current.merge(style)
    val resolvedColor = when {
        color.isSpecified -> color
        mergedStyle.color.isSpecified -> mergedStyle.color
        else -> LocalContentColor.current
    }
    BasicText(
        text = text,
        modifier = modifier,
        style = mergedStyle.copy(color = resolvedColor),
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}
