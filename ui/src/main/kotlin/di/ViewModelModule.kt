package di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import screens.connection.ConnectViewModel
import screens.server.ServerViewModel
import screens.server.catalog.CatalogViewModel
import screens.server.connectionsettings.ConnectionSettingsViewModel
import screens.splash.SplashViewModel

val provideViewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::ConnectViewModel)
    viewModelOf(::ServerViewModel)
    viewModelOf(::ConnectionSettingsViewModel)
    viewModelOf(::CatalogViewModel)
}