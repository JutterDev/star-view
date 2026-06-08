package screens.server

import common.BaseViewModel
import kotlinx.coroutines.delay
import screens.connection.ConnectEvent
import screens.splash.SplashAction
import screens.splash.SplashEvent
import screens.splash.SplashState
import usecases.serverconnection.IGetServerInfoUC

class ServerViewModel(
    private val getServerInfoUC: IGetServerInfoUC,
): BaseViewModel<ServerState, ServerEvent>() {

    override fun startState() = ServerState()

    init {
        launchUI {
            val server = withIO { getServerInfoUC() }
            _uiState.emit(uiState.value.copy(
                ip = server.ip,
                port = server.port,
                pointName = server.pointName
            ))
        }
    }

    fun onAction(action: ServerAction) {
        when(action) {
            ServerAction.OpenCatalog -> openCatalog()
            ServerAction.OpenConnectionSettings -> openSettings()
            ServerAction.ReloadList -> reloadList()
        }
    }

    private fun openSettings() {
        launchIO {
            _uiEvents.emit(ServerEvent.Navigation.OpenConnectionSettings)
        }
    }

    private fun openCatalog() {
        launchIO {
            _uiEvents.emit(ServerEvent.Navigation.OpenCatalog)
        }
    }

    private fun reloadList() {
        launchUI {
            _uiState.emit(uiState.value.copy(
                listState = ListState.Loading,
            ))
            withIO { delay(1000) }
            _uiState.emit(uiState.value.copy(
                listState = ListState.Error("Stupid children detected: code 300"),
            ))
        }
    }
}