package screens.splash

import common.BaseViewModel
import kotlinx.coroutines.delay
import usecases.serverconnection.IIsConnectionSettingsSavedUC

class SplashViewModel(
    private val isConnectionSettingsSavedUC: IIsConnectionSettingsSavedUC,
): BaseViewModel<SplashState, SplashEvent>() {

    override fun startState() = SplashState

    init {
        launchIO {
            delay(3_000L)
            withIO {
                if (isConnectionSettingsSavedUC()) {
                    _uiEvents.emit(
                        SplashEvent.SplashNavigation.NavigateToMain
                    )
                } else {
                    _uiEvents.emit(
                        SplashEvent.SplashNavigation.NavigateToConnect
                    )
                }
            }
        }
    }

    fun onAction(action: SplashAction) {

    }
}