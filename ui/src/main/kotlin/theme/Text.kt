package theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import theme.MonitorText.Bold.Sp18

sealed class MonitorText(
    val color: Color,
    val fontSize: TextUnit,
    val fontWeight: FontWeight,
) {


    sealed class Bold(
        color: Color,
        fontSize: TextUnit,
    ): MonitorText(color, fontSize, FontWeight.Bold) {

        sealed class Sp64(color: Color): Bold(color, 64.sp) {
            object White: Sp64(color = Colors.textPrimary)
            object Gray: Sp64(color = Colors.textSecondary)
        }
        sealed class Sp48(color: Color): Bold(color, 48.sp) {
            object White: Sp48(color = Colors.textPrimary)
            object Gray: Sp48(color = Colors.textSecondary)
        }
        sealed class Sp32(color: Color): Bold(color, 32.sp) {
            object White: Sp32(color = Colors.textPrimary)
            object Gray: Sp32(color = Colors.textSecondary)
        }
        sealed class Sp24(color: Color): Bold(color, 24.sp) {
            object White: Sp24(color = Colors.textPrimary)
            object Gray: Sp24(color = Colors.textSecondary)
        }
        sealed class Sp20(color: Color): Bold(color, 20.sp) {
            object White: Sp20(color = Colors.textPrimary)
            object Gray: Sp20(color = Colors.textSecondary)
        }
        sealed class Sp18(color: Color): Bold(color, 18.sp) {
            object White: Sp18(color = Colors.textPrimary)
            object Red: Sp18(color = Colors.textError)
            object Green: Sp18(color = Colors.textGreen)
            object Gray: Sp18(color = Colors.textSecondary)
        }
        sealed class Sp16(color: Color): Bold(color, 16.sp) {
            object White: Sp16(color = Colors.textPrimary)
            object Gray: Sp16(color = Colors.textSecondary)
        }
        sealed class Sp15(color: Color): Bold(color, 15.sp) {
            object White: Sp15(color = Colors.textPrimary)
            object Gray: Sp15(color = Colors.textSecondary)
        }
        sealed class Sp14(color: Color): Bold(color, 14.sp) {
            object White: Sp14(color = Colors.textPrimary)
            object Gray: Sp14(color = Colors.textSecondary)
        }
    }

    sealed class Regular(
        color: Color,
        fontSize: TextUnit,
    ): MonitorText(color, fontSize, FontWeight.Normal) {

        sealed class Sp64(color: Color): Regular(color, 64.sp) {
            object White: Sp64(color = Colors.textPrimary)
            object Gray: Sp64(color = Colors.textSecondary)
        }
        sealed class Sp48(color: Color): Regular(color, 48.sp) {
            object White: Sp48(color = Colors.textPrimary)
            object Gray: Sp48(color = Colors.textSecondary)
        }
        sealed class Sp32(color: Color): Regular(color, 32.sp) {
            object White: Sp32(color = Colors.textPrimary)
            object Gray: Sp32(color = Colors.textSecondary)
        }
        sealed class Sp24(color: Color): Regular(color, 24.sp) {
            object White: Sp24(color = Colors.textPrimary)
            object Gray: Sp24(color = Colors.textSecondary)
        }
        sealed class Sp20(color: Color): Regular(color, 20.sp) {
            object White: Sp20(color = Colors.textPrimary)
            object Gray: Sp20(color = Colors.textSecondary)
        }
        sealed class Sp18(color: Color): Regular(color, 18.sp) {
            object White: Sp18(color = Colors.textPrimary)
            object Red: Sp18(color = Colors.textError)
            object Green: Sp18(color = Colors.textGreen)
            object Gray: Sp18(color = Colors.textSecondary)
        }
        sealed class Sp16(color: Color): Regular(color, 16.sp) {
            object White: Sp16(color = Colors.textPrimary)
            object Gray: Sp16(color = Colors.textSecondary)
            object Red: Sp16(color = Colors.textError)
        }
        sealed class Sp15(color: Color): Regular(color, 15.sp) {
            object White: Sp15(color = Colors.textPrimary)
            object Gray: Sp15(color = Colors.textSecondary)
        }
        sealed class Sp14(color: Color): Regular(color, 14.sp) {
            object White: Sp14(color = Colors.textPrimary)
            object Gray: Sp14(color = Colors.textSecondary)
        }
        sealed class Sp12(color: Color): Regular(color, 12.sp) {
            object White: Sp12(color = Colors.textPrimary)
            object Gray: Sp12(color = Colors.textSecondary)
        }
    }

    fun style() = TextStyle.Default.copy(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
    )
}

private fun createStyle(
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
) = TextStyle.Default.copy(
    color = color,
    fontSize = fontSize,
    fontWeight = fontWeight,
)