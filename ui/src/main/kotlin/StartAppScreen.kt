import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import online.jutter.navigation.Catalog
import online.jutter.navigation.ConnectDestination
import online.jutter.navigation.ConnectionSettings
import online.jutter.navigation.ServerDestination
import online.jutter.navigation.SplashDestination
import screens.connection.ConnectScreen
import screens.server.ServerScreen
import screens.server.catalog.CatalogScreen
import screens.server.connectionsettings.ConnectionSettingsScreen
import screens.splash.SplashScreen

@Composable
fun StartAppScreen() {

    val navController = rememberNavController()

    NavHost(navController, SplashDestination) {
        composable<SplashDestination> {
            SplashScreen(navController)
        }
        composable<ConnectDestination> {
            ConnectScreen(navController)
        }
        composable<ServerDestination> {
            ServerScreen(navController)
        }
        composable<ConnectionSettings> {
            ConnectionSettingsScreen(navController)
        }
        composable<Catalog> {
            CatalogScreen(navController)
        }
    }
}