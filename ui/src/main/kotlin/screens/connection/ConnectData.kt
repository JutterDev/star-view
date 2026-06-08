package screens.connection

data class ConnectState(
    val connectError: String? = null,
    val buttonEnabled: Boolean = false,
    val progress: Boolean = false,
)

sealed class ConnectAction {

    data class IpChanged(
        val ip: String,
    ): ConnectAction()

    data class PortChanged(
        val port: String,
    ): ConnectAction()

    data class KeyChanged(
        val key: String,
    ): ConnectAction()

    object ConnectButtonClicked: ConnectAction()
}

sealed class ConnectEvent {

    sealed class Navigation: ConnectEvent() {

        object Main: Navigation()
    }
}