package common.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource

@Composable
fun SvgIcon(
    svgName: String,
    modifier: Modifier = Modifier
) {

    val svgPainter = painterResource("drawables/$svgName.svg")

    Canvas(
        modifier = modifier,
    ) {
        with(svgPainter) {
            draw(size = Size(size.width, size.height))
        }
    }
}