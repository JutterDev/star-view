package common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import theme.Colors
import theme.MonitorText

@Composable
fun PrimaryButton(
    text: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    loaderVisible: Boolean = false,
    corners: Dp = 16.dp,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        modifier = modifier
            .height(54.dp),
        shape = RoundedCornerShape(corners),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.primary,
            disabledBackgroundColor = Colors.backgroundSecondary,
//            containerColor = Colors.primary,
//            disabledContainerColor = Colors.backgroundSecondary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatedVisibility(loaderVisible) {
                CircularProgressIndicator(
                    color = Colors.white,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
            Text(
                text = text,
                style = if (enabled) {
                    MonitorText.Regular.Sp18.White.style()
                } else {
                    MonitorText.Regular.Sp18.Gray.style()
                }
            )
        }
    }
}
