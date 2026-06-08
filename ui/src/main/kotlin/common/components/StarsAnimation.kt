package common.components
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.AppTheme
import theme.Colors
import kotlin.math.PI
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun StarsAnimation(
    modifier: Modifier,
    bottomGradient: Boolean = false,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val infinitelyAnimatedFloat = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(10000),
            repeatMode = RepeatMode.Restart
        )
    )
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val stars: List<Star> = remember {
            buildList {
                repeat(400) {
                    val x = Random.nextFloat()
                    val y = Random.nextFloat()
                    val alpha = (Random.nextFloat() * 2.0 * PI).toFloat()
                    val isLight = Random.nextFloat() < 0.02
                    add(Star(
                        x, y, alpha,
                        isLight,
                        if (isLight) Random.nextFloat() < 0.5 else false,
                    ))
                }
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            val foregroundBrush= Brush.verticalGradient(listOf(Colors.transparent, Colors.background))
            for (star in stars) {
                star.update(infinitelyAnimatedFloat.value)
                if (star.nowLight) {
                    val starBrush = Brush.radialGradient(
                        colors = listOf(Colors.primary, Color.Transparent),
                        center = Offset(star.x * size.width, star.y * size.height),
                    )
                    drawCircle(
                        brush = starBrush,
                        radius = size.width / 2,
                        center = Offset(star.x * size.width, star.y * size.height),
                        alpha = star.alpha
                    )
                }
                drawCircle(
                    color = Color.White,
                    center = Offset(star.x * size.width, star.y * size.height),
                    radius = 1f,
                    alpha = star.alpha,
                )
            }
            if (bottomGradient) drawRect(foregroundBrush)
        }
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

data class Star(
    var x: Float,
    var y: Float,
    var alpha: Float,
    var isLight: Boolean = false,
    var nowLight: Boolean = false,
) {
    private val initialAlpha = alpha

    fun update(value: Float) {
        val x = (value - initialAlpha).toDouble()
        val newAlpha = 0.5f + (0.5f * sin(x).toFloat())
        if (alpha == 0.5f && isLight) nowLight = !nowLight
        alpha = newAlpha
    }
}

@Preview
@Composable
fun MusicVisualiserPreview() {
    AppTheme {
        StarsAnimation(
            modifier = Modifier.size(
                width = 400.dp,
                height = 400.dp,
            )
        )
    }
}