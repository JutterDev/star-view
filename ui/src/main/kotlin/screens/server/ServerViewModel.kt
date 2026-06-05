package screens.server

import common.BaseViewModel
import kotlinx.coroutines.delay
import screens.splash.SplashAction
import screens.splash.SplashEvent
import screens.splash.SplashState

class ServerViewModel: BaseViewModel<ServerState, ServerEvent>() {

    override fun startState() = ServerState

    init {
        launchIO {
            delay(3_000L)
            withIO {
//                _uiEvents.emit(
//                    SplashEvent.SplashNavigation.NavigateToConnect
//                )
            }
        }
    }

    fun onAction(action: SplashAction) {

    }
}