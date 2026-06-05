package usecases.serverconnection

import online.jutter.datacontracts.SettingsRepository

interface IClearServerConnectionSettingsUC {

    suspend operator fun invoke()
}

class ClearServerConnectionSettingsUC(
    private val settingsRepository: SettingsRepository,
): IClearServerConnectionSettingsUC {

    override suspend fun invoke() {
        settingsRepository.ip = null
        settingsRepository.port = null
    }
}