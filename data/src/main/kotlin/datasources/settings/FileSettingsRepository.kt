package datasources.settings

import online.jutter.datacontracts.SettingsRepository

class FileSettingsRepository: SettingsRepository {

    companion object {
        private const val KEY_IP = "key_ip"
        private const val KEY_PORT = "key_port"
    }

    private val settings = FileSettings("StarView")

    override var ip: String?
        get() = settings.getString(KEY_IP)
        set(value) = settings.saveString(KEY_IP, value)

    override var port: String?
        get() = settings.getString(KEY_PORT)
        set(value) = settings.saveString(KEY_PORT, value)

}