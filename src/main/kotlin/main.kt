import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import di.provideViewModelModule
import di.provideDataModule
import di.provideDomainModule
import org.koin.compose.KoinApplication
import theme.Colors
import theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Colors.background)
        ) {
            StartAppScreen()
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "monitor",
        undecorated = true,
        transparent = true
    ) {
        Column(modifier = Modifier.fillMaxSize().background(Colors.backgroundSecondary)) {

            WindowDraggableArea {
                Surface(
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    color = Colors.backgroundSecondary,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Star View", color = Colors.textPrimary)

                        Row {
                            IconButton(
                                onClick = { window.isMinimized = true },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Text("—", color = Colors.textPrimary)
                            }

                            IconButton(
                                onClick = {
                                    window.placement = if (window.placement == WindowPlacement.Maximized) {
                                        WindowPlacement.Floating
                                    } else {
                                        WindowPlacement.Maximized
                                    }
                                },
                                modifier = Modifier.size(30.dp)
                            ) {
                                val icon = if (window.placement == WindowPlacement.Maximized) "❐" else "☐"
                                Text(icon, color = Colors.textPrimary)
                            }

                            IconButton(
                                onClick = { exitApplication() },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Text("✕", color = Colors.textError)
                            }
                        }
                    }
                }
            }

            Box(Modifier
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = Colors.backgroundSecondary,
                    shape = RoundedCornerShape(16.dp),
                )
            ) {
                KoinApplication(application = {
                    modules(
                        listOf(
                            provideDataModule,
                            provideViewModelModule,
                            provideDomainModule,
                        )
                    )
                }) {
                    App()
                }
            }
        }
    }
}
