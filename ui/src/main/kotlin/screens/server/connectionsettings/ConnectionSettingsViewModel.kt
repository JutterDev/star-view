package screens.server.connectionsettings

import common.BaseViewModel
import screens.connection.ConnectEvent
import usecases.serverconnection.IGetServerInfoUC
import usecases.serverconnection.ISaveServerConnectionSettingsUC

class ConnectionSettingsViewModel(
    private val saveServerConnectionSettingsUC: ISaveServerConnectionSettingsUC,
): BaseViewModel<ConnectionSettingsState, ConnectionSettingsEvent>() {

    override fun startState() = ConnectionSettingsState()

    private var ip: String = ""
    private var port: String = ""
    private var key: String = ""

    fun onAction(action: ConnectionSettingsAction) {
        when(action) {
            ConnectionSettingsAction.UpdateButtonClicked -> onUpdate()
            ConnectionSettingsAction.CancelButtonClicked -> onCancel()
            is ConnectionSettingsAction.IpChanged -> onIpChanged(action.ip)
            is ConnectionSettingsAction.PortChanged -> onPortChanged(action.port)
            is ConnectionSettingsAction.KeyChanged -> onKeyChanged(action.key)
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

    fun onUpdate() {
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
            _uiEvents.emit(ConnectionSettingsEvent.Navigation.Back)
        }
    }

    fun onCancel() {
        launchUI {
            withIO { _uiEvents.emit(ConnectionSettingsEvent.Navigation.Back) }
        }
    }
}
