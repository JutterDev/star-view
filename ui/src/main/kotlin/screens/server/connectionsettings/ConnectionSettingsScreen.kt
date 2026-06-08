package screens.server.connectionsettings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import common.SingleEventEffect
import common.components.PrimaryButton
import common.components.SvgIcon
import common.components.TextInputLine
import kotlinx.coroutines.flow.SharedFlow
import online.jutter.navigation.ServerDestination
import org.koin.compose.viewmodel.koinViewModel
import screens.connection.ConnectAction
import screens.connection.ConnectEvent
import screens.connection.ConnectState
import screens.connection.ConnectViewModel
import theme.Colors
import theme.MonitorText

@Composable
fun ConnectionSettingsScreen(
    navController: NavController,
    viewModel: ConnectionSettingsViewModel = koinViewModel()
) {
    ConnectionSettingsContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        events = viewModel.uiEvents,
        onAction = viewModel::onAction,
        onNavigation = { navigation ->
            when(navigation) {
                ConnectionSettingsEvent.Navigation.Back -> {
                    navController.popBackStack()
                }
            }
        }
    )
}

@Composable
fun ConnectionSettingsContent(
    state: ConnectionSettingsState,
    events: SharedFlow<ConnectionSettingsEvent>,
    onAction: (ConnectionSettingsAction) -> Unit,
    onNavigation: (ConnectionSettingsEvent.Navigation) -> Unit,
) {

    SingleEventEffect(events) { event ->
        when(event) {
            is ConnectionSettingsEvent.Navigation -> onNavigation(event)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Update connection settings",
                        style = MonitorText.Bold.Sp24.White.style(),
                        modifier = Modifier
                            .weight(1f)
                    )
                    SvgIcon(
                        svgName = "settings_icon",
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(Modifier.height(20.dp))
                TextInputLine(
                    label = "IP Address",
                    keyboardType = KeyboardType.Text,
                ) {
                    onAction(ConnectionSettingsAction.IpChanged(it))
                }
                TextInputLine(
                    label = "Port",
                    keyboardType = KeyboardType.Decimal,
                ) {
                    onAction(ConnectionSettingsAction.PortChanged(it))
                }
                TextInputLine(
                    label = "Secret Key",
                    keyboardType = KeyboardType.Text,
                ) {
                    onAction(ConnectionSettingsAction.KeyChanged(it))
                }
                AnimatedVisibility(state.connectError != null) {
                    Box(
                        Modifier.background(Colors.errorBackground, RoundedCornerShape(16.dp))
                            .padding(16.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = state.connectError ?: "Error",
                            style = MonitorText.Regular.Sp16.Red.style(),
                            maxLines = 10,
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                PrimaryButton(
                    text = "Cancel",
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Colors.textError,
                ) {
                    onAction(ConnectionSettingsAction.CancelButtonClicked)
                }
                PrimaryButton(
                    text = "Update",
                    enabled = state.buttonEnabled,
                    loaderVisible = state.progress,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    onAction(ConnectionSettingsAction.UpdateButtonClicked)
                }
            }
        }
    }
}