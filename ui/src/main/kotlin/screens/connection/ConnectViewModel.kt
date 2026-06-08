package screens.connection

import common.BaseViewModel
import usecases.serverconnection.ISaveServerConnectionSettingsUC

class ConnectViewModel(
    private val saveServerConnectionSettingsUC: ISaveServerConnectionSettingsUC,
): BaseViewModel<ConnectState, ConnectEvent>() {

    override fun startState() = ConnectState()

    private var ip: String = ""
    private var port: String = ""
    private var key: String = ""

    fun onAction(action: ConnectAction) {
        when(action) {
            ConnectAction.ConnectButtonClicked -> onLogin()
            is ConnectAction.IpChanged -> onIpChanged(action.ip)
            is ConnectAction.PortChanged -> onPortChanged(action.port)
            is ConnectAction.KeyChanged -> onKeyChanged(action.key)
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

    private fun onKeyChanged(key: String) {
        launchUI {
            this.key = key
            updateLoginButtonState()
        }
    }

    private suspend fun updateLoginButtonState() {
        _uiState.emit(
            uiState.value.copy(
                buttonEnabled = ip.isNotBlank() && port.isNotBlank() && key.isNotBlank()
            )
        )
    }

    fun onLogin() {
        launchUI({
            it.printStackTrace()
            _uiState.emit(uiState.value.copy(
                connectError = it.message,
                progress = false,
            )
        ) }) {
            _uiState.emit(uiState.value.copy(
                progress = true,
            ))
            withIO {
                saveServerConnectionSettingsUC(ip, port, key)
            }
            _uiState.emit(uiState.value.copy(
                progress = false,
            ))
            _uiEvents.emit(ConnectEvent.Navigation.Main)
        }
    }
}
