package screens.splash

data object SplashState

data object SplashAction

sealed class SplashEvent {

    sealed class SplashNavigation: SplashEvent() {

        data object NavigateToMain: SplashNavigation()

        data object NavigateToConnect: SplashNavigation()
    }
}