package online.jutter.navigation

import kotlinx.serialization.Serializable

interface NavigationDestination

@Serializable
object SplashDestination: NavigationDestination

@Serializable
object  ConnectDestination: NavigationDestination

@Serializable
object  ServerDestination: NavigationDestination

@Serializable
object  ConnectionSettings: NavigationDestination

@Serializable
object  Catalog: NavigationDestination