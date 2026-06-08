package screens.server.connectionsettings

data class ConnectionSettingsState(
    val connectError: String? = null,
    val buttonEnabled: Boolean = false,
    val progress: Boolean = false,
)

sealed class ConnectionSettingsAction {

    data class IpChanged(
        val ip: String,
    ): ConnectionSettingsAction()

    data class PortChanged(
        val port: String,
    ): ConnectionSettingsAction()

    data class KeyChanged(
        val key: String,
    ): ConnectionSettingsAction()

    object UpdateButtonClicked: ConnectionSettingsAction()


    object CancelButtonClicked: ConnectionSettingsAction()
}

sealed class ConnectionSettingsEvent {

    sealed class Navigation: ConnectionSettingsEvent() {

        object Back: Navigation()
    }
}