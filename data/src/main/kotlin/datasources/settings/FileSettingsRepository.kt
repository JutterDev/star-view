package datasources.settings

import datacontracts.SettingsRepository

class FileSettingsRepository: SettingsRepository {

    companion object {
        private const val KEY_IP = "key_ip"
        private const val KEY_PORT = "key_port"
        private const val KEY_Key = "key_key"
        private const val KEY_POINT_NAME = "key_point_name"
    }

    private val settings = FileSettings("StarView")

    override var ip: String?
        get() = settings.getString(KEY_IP)
        set(value) = settings.saveString(KEY_IP, value)

    override var port: String?
        get() = settings.getString(KEY_PORT)
        set(value) = settings.saveString(KEY_PORT, value)

    override var key: String?
        get() = settings.getString(KEY_Key)
        set(value) = settings.saveString(KEY_Key, value)

    override var pointName: String?
        get() = settings.getString(KEY_POINT_NAME)
        set(value) = settings.saveString(KEY_POINT_NAME, value)

}