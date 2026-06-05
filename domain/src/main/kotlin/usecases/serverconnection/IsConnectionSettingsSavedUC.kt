package usecases.serverconnection

import online.jutter.datacontracts.SettingsRepository

interface IIsConnectionSettingsSavedUC {

    suspend operator fun invoke(): Boolean
}

class IsConnectionSettingsSavedUC(
    private val settingsRepository: SettingsRepository,
): IIsConnectionSettingsSavedUC {

    override suspend fun invoke() =
        (settingsRepository.ip?.isNotEmpty() ?: false) && (settingsRepository.port?.isNotEmpty() ?: false)
}