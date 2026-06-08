package screens.server

import common.BaseViewModel
import kotlinx.coroutines.delay
import models.telescope.TelescopeInfo
import usecases.serverconnection.IGetServerInfoUC
import usecases.telescope.IGetTelescopesListUC

class ServerViewModel(
    private val getServerInfoUC: IGetServerInfoUC,
    private val getTelescopesListUC: IGetTelescopesListUC,
): BaseViewModel<ServerState, ServerEvent>() {

    private var telescopes = listOf<TelescopeInfo>()

    override fun startState() = ServerState()

    init {
        showServerInfo()
        loadTelescopes()
    }

    private fun showServerInfo() {
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
            ServerAction.ReloadList -> loadTelescopes()
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

    private fun loadTelescopes() {
        launchUI(
            onError = {
                withIO { delay(1000) }
                _uiState.emit(uiState.value.copy(
                    listState = ListState.Error(it.message.toString()),
                ))
            }
        ) {
            _uiState.emit(uiState.value.copy(
                listState = ListState.Loading,
            ))
            telescopes = withIO { getTelescopesListUC() }
            if (telescopes.isEmpty()) {
                withIO { delay(1000) }
                _uiState.emit(
                    uiState.value.copy(
                        listState = ListState.Empty,
                    )
                )
            } else {
                _uiState.emit(
                    uiState.value.copy(
                        listState = ListState.Ready(telescopes),
                    )
                )
            }
        }
    }
}