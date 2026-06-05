package common.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Colors
import theme.MonitorText
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key // расширение для KeyEvent
import androidx.compose.ui.input.key.type // расширение для KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent

@Composable
fun TextInputLine(
    placeholder: String? = null,
    label: String? = null,
    text: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    onDoneAction: (() -> Unit)? = null,
    clearAfterEnter: Boolean = false,
    onTextChange: (String) -> Unit

) {

    var value by remember { mutableStateOf(TextFieldValue(text ?: "")) }
    val labelSize: Float by animateFloatAsState(if (value.text.isEmpty()) 16f else 12f, label = "size")
    val labelPadding: Float by animateFloatAsState(if (value.text.isEmpty()) 0f else 8f, label = "padding")
    TextField(
        value = value,
        placeholder = if (!placeholder.isNullOrEmpty()) {{
            Text(
                text = placeholder,
                style = MonitorText.Regular.Sp16.Gray.style(),
            )
        }} else null,
        label = if (!label.isNullOrEmpty()) {{
            Text(
                text = label,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    color = Colors.textSecondary,
                    fontSize = labelSize.sp,
                ),
                modifier = Modifier.padding(bottom = labelPadding.dp)
            )
        }} else null,
        onValueChange = { newText ->
            value = newText
            onTextChange(newText.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.backgroundSecondary, RoundedCornerShape(16.dp))
            .padding(6.dp)
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyDown) {
                    onDoneAction?.invoke()
                    if (clearAfterEnter) value = TextFieldValue("")
                    true
                } else {
                    false
                }
            },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Colors.backgroundSecondary,
            focusedIndicatorColor = Colors.transparent,
            unfocusedIndicatorColor = Colors.transparent,
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        textStyle = MonitorText.Regular.Sp16.White.style(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions(
            onDone = {
                onDoneAction?.invoke()
                if (clearAfterEnter) value = TextFieldValue("")
            }
        )
    )
}
