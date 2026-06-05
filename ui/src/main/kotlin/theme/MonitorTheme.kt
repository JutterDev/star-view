package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.letsPlot.core.spec.plotson.theme

@Composable
fun MonitorTheme(
    content: @Composable () -> Unit,
) {
//
//    val colors = lightColorScheme(
//        background = Colors.background,
//        primary = Colors.primary,
//        secondary = Colors.secondary,
//        tertiary = Colors.tertiary,
//    )
//
//    val typography = Typography(
//        bodyMedium = TextStyle(
//            fontFamily = FontFamily.Default,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp
//        )
//    )
//    val shapes = Shapes(
//        small = RoundedCornerShape(4.dp),
//        medium = RoundedCornerShape(4.dp),
//        large = RoundedCornerShape(0.dp)
//    )

    MaterialTheme(
        colors = Colors(
            background = theme.Colors.background,
            primary = theme.Colors.primary,
            primaryVariant = theme.Colors.primary,
            secondary = theme.Colors.secondary,
            secondaryVariant = theme.Colors.secondary,
            surface = theme.Colors.primary,
            error = theme.Colors.primary,
            onPrimary = theme.Colors.primary,
            onSecondary = theme.Colors.primary,
            onBackground = theme.Colors.primary,
            onSurface = theme.Colors.primary,
            onError = theme.Colors.primary,
            isLight = true,
        ),
        content = content
    )
}