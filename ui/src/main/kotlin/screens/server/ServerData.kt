package screens.server

data class ServerState(
    val ip: String = "ip",
    val port: String = "port",
    val pointName: String = "name",
    val listState: ListState = ListState.Error("Stupid children detected: code 300"),
)

sealed class ListState {

    data object Loading: ListState()

    data class Ready(
        val int: Int,
    ): ListState()

    data class Error(
        val errorText: String,
    ): ListState()

    data object Empty: ListState()
}

sealed class ServerAction {

    object OpenConnectionSettings: ServerAction()

    object OpenCatalog: ServerAction()

    object ReloadList: ServerAction()
}

sealed class ServerEvent {

    sealed class Navigation: ServerEvent() {

        data object OpenConnectionSettings: Navigation()

        data object OpenCatalog: Navigation()
    }
}