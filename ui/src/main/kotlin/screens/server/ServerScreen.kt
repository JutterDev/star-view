package screens.server

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import common.SingleEventEffect
import common.components.MCard
import common.components.PrimaryButton
import common.components.StarsAnimation
import common.components.SvgIcon
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import online.jutter.navigation.Catalog
import online.jutter.navigation.ConnectionSettings
import org.koin.compose.viewmodel.koinViewModel
import screens.connection.ConnectAction
import theme.Colors
import theme.MonitorText

@Composable
fun ServerScreen(
    navController: NavController,
    viewModel: ServerViewModel = koinViewModel()
) {
    ServerScreenContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        events = viewModel.uiEvents,
        onAction = viewModel::onAction,
        onNavigation = {
            when(it) {
                ServerEvent.Navigation.OpenCatalog -> navController.navigate(Catalog)
                ServerEvent.Navigation.OpenConnectionSettings -> navController.navigate(ConnectionSettings)
            }
        }
    )
}

@Composable
fun ServerScreenContent(
    state: ServerState,
    events: SharedFlow<ServerEvent>,
    onAction: (ServerAction) -> Unit,
    onNavigation: (ServerEvent.Navigation) -> Unit,
) {

    SingleEventEffect(events) { event ->
        when (event) {
            is ServerEvent.Navigation -> onNavigation(event)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier.size(560.dp, 700.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max),
                    ) {
                        MCard(
                            modifier = Modifier
                                .weight(1.0f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                SvgIcon(
                                    svgName = "star_icon",
                                    modifier = Modifier.size(62.dp),
                                )
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(
                                        text = state.pointName,
                                        style = MonitorText.Bold.Sp24.White.style(),
                                    )
                                    Text(
                                        text = "${state.ip}:${state.port}",
                                        style = MonitorText.Regular.Sp18.Gray.style(),
                                    )
                                }
                            }
                        }
                        MCard(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1.0f),
                            onClick = { onAction(ServerAction.OpenConnectionSettings) }
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                SvgIcon(
                                    svgName = "settings_icon",
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                width = 2.dp,
                                color = Colors.backgroundSecondary, // Укажите нужный цвет рамки
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onAction(ServerAction.OpenCatalog)
                            }
                    ) {
                        StarsAnimation(
                            modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(Modifier.weight(1f))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(0.dp, 24.dp, 0.dp, 0.dp))
                                    .background(Colors.backgroundSecondary)
                                    .padding(24.dp)
                            ) {
                                SvgIcon(
                                    svgName = "file_icon",
                                    modifier = Modifier.size(24.dp),
                                )
                                Text(
                                    text = "Sky objects catalog",
                                    style = MonitorText.Regular.Sp20.White.style(),
                                )
                            }
                        }
                    }
                }

                MCard(
                    modifier = Modifier
                        .size(560.dp, 700.dp)
                ) {
                    when(state.listState) {
                        is ListState.Empty -> ListEmpty(state.listState, onAction)
                        is ListState.Error -> ListError(state.listState, onAction)
                        is ListState.Loading -> ListLoading(state.listState, onAction)
                        is ListState.Ready -> ListReady(state.listState, onAction)
                    }
                }

            }
        }
    }
}

@Composable
fun ListReady(
    state: ListState.Ready,
    onAction: (ServerAction) -> Unit,
) {

}

@Composable
fun ListLoading(
    state: ListState.Loading,
    onAction: (ServerAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = Colors.white,
            modifier = Modifier
                .size(24.dp)
        )
    }
}

@Composable
fun ListEmpty(
    state: ListState.Empty,
    onAction: (ServerAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(280.dp)
        ) {
            SvgIcon(
                svgName = "empty_icon",
                modifier = Modifier.size(80.dp),
            )
            Text(
                text = "Devices not found",
                style = MonitorText.Regular.Sp18.White.style(),
            )
            Text(
                text = "Connect the device to the same network as the server and they will find each other themselves",
                style = MonitorText.Regular.Sp16.Gray.style(),
            )
            PrimaryButton(
                text = "Try again",
                modifier = Modifier.fillMaxWidth(),
            ) {
                onAction(ServerAction.ReloadList)
            }
        }
    }
}

@Composable
fun ListError(
    state: ListState.Error,
    onAction: (ServerAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.width(280.dp)
        ) {
            SvgIcon(
                svgName = "network_error_icon",
                modifier = Modifier.size(80.dp),
            )
            Text(
                text = "Network error",
                style = MonitorText.Regular.Sp18.White.style(),
            )
            Text(
                text = "Please fix your network issues and try again",
                style = MonitorText.Regular.Sp16.Gray.style(),
            )
            Box(
                Modifier.background(Colors.errorBackground, RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = state.errorText,
                    style = MonitorText.Regular.Sp16.Red.style(),
                    maxLines = 10,
                )
            }
            PrimaryButton(
                text = "Try again",
                modifier = Modifier.fillMaxWidth(),
            ) {
                onAction(ServerAction.ReloadList)
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    ServerScreenContent(
        state = ServerState(),
        onAction = {},
        events = MutableSharedFlow(),
        onNavigation = {},
    )
}