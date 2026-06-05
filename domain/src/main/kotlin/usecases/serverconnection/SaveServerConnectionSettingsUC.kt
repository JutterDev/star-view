package usecases.serverconnection

import online.jutter.datacontracts.SettingsRepository

interface ISaveServerConnectionSettingsUC {

    suspend operator fun invoke(ip: String, port: String)
}

class SaveServerConnectionSettingsUC(
    private val settingsRepository: SettingsRepository,
): ISaveServerConnectionSettingsUC {

    override suspend fun invoke(ip: String, port: String) {
        settingsRepository.ip = ip
        settingsRepository.port = port
    }
}