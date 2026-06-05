package datasources.settings

import java.io.File

class FileSettings(appName: String) {

    private val configDir = File(System.getProperty("user.home"), ".$appName")
    private val configFile = File(configDir, "settings.config")

    private val settings = readSettings()

    private fun readSettings(): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        if (configFile.exists()) {
            configFile.forEachLine {
                val entry = it.split("=")
                map[entry[0]] = entry[1]
            }
        }
        return map
    }

    private fun saveSettings() {
        if (!configDir.exists()) {
            configDir.mkdirs()
        }
        configFile.bufferedWriter().use { writer ->
            settings.forEach { entry ->
                writer.write("${entry.key}=${entry.value}")
                writer.newLine()
            }
        }
    }

    fun saveString(key: String, value: String?) {
        if (value == null) {
            settings.remove(key)
        } else {
            settings[key] = value
        }
        saveSettings()
    }

    fun getString(key: String): String? {
        return settings[key]
    }

    fun saveInt(key: String, value: Int) = saveString(key, value.toString())

    fun getInt(key: String): Int? = getString(key)?.toInt()

}