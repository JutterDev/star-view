package screens.server

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import common.SingleEventEffect
import common.components.SvgIcon
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.compose.viewmodel.koinViewModel
import screens.splash.SplashAction
import screens.splash.SplashEvent
import screens.splash.SplashState
import screens.splash.SplashViewModel
import theme.Colors

@Composable
fun ServerScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel()
) {
    ServerScreenContent(
        state = viewModel.uiState.collectAsStateWithLifecycle().value,
        events = viewModel.uiEvents,
        onAction = viewModel::onAction,
        onNavigation = {
//            when(it) {
//
//            }
        }
    )
}

@Composable
fun ServerScreenContent(
    state: SplashState,
    events: SharedFlow<SplashEvent>,
    onAction: (SplashAction) -> Unit,
    onNavigation: (SplashEvent.SplashNavigation) -> Unit,
) {

    SingleEventEffect(events) { event ->
        when (event) {
            is SplashEvent.SplashNavigation -> onNavigation(event)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                SvgIcon(
                    svgName = "star_icon",
                    modifier = Modifier.size(76.dp),
                )

                Text(
                    "Server",
                    fontSize = 64.sp,
                    color = Colors.white,
                )

            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    ServerScreenContent(
        state = SplashState,
        onAction = {},
        events = MutableSharedFlow(),
        onNavigation = {},
    )
}