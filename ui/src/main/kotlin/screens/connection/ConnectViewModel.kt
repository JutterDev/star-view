package screens.connection

import common.BaseViewModel
import usecases.serverconnection.ISaveServerConnectionSettingsUC

class ConnectViewModel(
    private val saveServerConnectionSettingsUC: ISaveServerConnectionSettingsUC,
): BaseViewModel<ConnectState, ConnectEvent>() {

    override fun startState() = ConnectState()

    private var ip: String = DEFAULT_IP
    private var port: String = DEFAULT_PORT

    fun onAction(action: ConnectAction) {
        when(action) {
            ConnectAction.ConnectButtonClicked -> onLogin()
            is ConnectAction.IpChanged -> onIpChanged(action.ip)
            is ConnectAction.PortChanged -> onPortChanged(action.port)
        }
    }

    private fun onIpChanged(ip: String) {
        launchUI {
            this.ip = ip
            updateLoginButtonState()
        }
    }

    private fun onPortChanged(port: String) {
        launchUI {
            this.port = port
            updateLoginButtonState()
        }
    }

    private suspend fun updateLoginButtonState() {
        _uiState.emit(
            uiState.value.copy(
                buttonEnabled = ip.isNotBlank() && port.isNotBlank()
            )
        )
    }

    fun onLogin() {
        launchUI({
            _uiState.emit(uiState.value.copy(
                connectError = it.message,
                progress = false,
            )
        ) }) {
            _uiState.emit(uiState.value.copy(
                progress = true,
            ))
            withIO {
                saveServerConnectionSettingsUC(ip, port)
            }
            _uiState.emit(uiState.value.copy(
                progress = false,
            ))
            _uiEvents.emit(ConnectEvent.Navigation.Main)
        }
    }
}

const val DEFAULT_IP = "192.168.0.100"
const val DEFAULT_PORT = "9999"