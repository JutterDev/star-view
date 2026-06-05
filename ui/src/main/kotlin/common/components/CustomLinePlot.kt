package common.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import theme.MonitorText
import kotlin.math.abs

@Composable
fun CustomLinePlot(
    plotLines: List<PlotLine>,
    modifier: Modifier = Modifier,
    title: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title, style = MonitorText.Regular.Sp18.White.style())
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Canvas(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val width = size.width
                val height = size.height

                val maxVal = plotLines.maxOf { it.y.maxOrNull() ?: 1.0 }
                val minVal = plotLines.maxOf { it.y.minOrNull() ?: 0.0 }

                drawRect(
                    color = Color.Black,
                    topLeft = Offset(0.5f, 0.5f),
                    size = Size(size.width - 1f, size.height - 1f),
                    style = Stroke(width = 1f)
                )

                drawLine(
                    color = Color.Black,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )

                val maxDeviation = maxOf(abs(maxVal), abs(minVal)).takeIf { it != 0.0 } ?: 1.0
                plotLines.forEach { plotLine ->
                    val range = maxVal - minVal
                    val spaceBetweenPoints = width / (plotLine.y.size - 1)

                    val strokePath = Path().apply {
                        plotLine.y.forEachIndexed { index, value ->
                            val x = index * spaceBetweenPoints
                            val yOffset = (value / maxDeviation) * (height / 2)
                            val y = (height / 2) - yOffset

                            if (index == 0) moveTo(x, y.toFloat()) else lineTo(x, y.toFloat())
                        }
                    }

                    drawPath(
                        path = strokePath,
                        color = plotLine.color,
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color(0xFF121212))
                    .padding(16.dp)
            ) {
                plotLines.forEach { plotLine ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Canvas(modifier = Modifier.size(16.dp, 16.dp)) {
                            drawCircle(
                                color = plotLine.color,
                                radius = size.minDimension / 2,
                                center = center
                            )
                        }
                        Text(plotLine.name, style = MonitorText.Regular.Sp16.Gray.style())
                    }
                }
            }
        }
    }
}

data class PlotLine(
    val x: List<Double>,
    val y: List<Double>,
    val color: Color,
    val name: String,
)