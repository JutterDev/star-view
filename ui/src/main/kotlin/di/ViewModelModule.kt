package di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import screens.connection.ConnectViewModel
import screens.server.ServerViewModel
import screens.splash.SplashViewModel

val provideViewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::ConnectViewModel)
    viewModelOf(::ServerViewModel)
}