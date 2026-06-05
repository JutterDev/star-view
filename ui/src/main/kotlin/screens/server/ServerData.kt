package screens.server

data object ServerState

data object ServerAction

sealed class ServerEvent {

    sealed class ServerNavigation: ServerEvent() {

        data object NavigateToMain: ServerNavigation()

        data object NavigateToConnect: ServerNavigation()
    }
}