package usecases.serverconnection

import datacontracts.SettingsRepository

interface IIsConnectionSettingsSavedUC {

    suspend operator fun invoke(): Boolean
}

class IsConnectionSettingsSavedUC(
    private val settingsRepository: SettingsRepository,
): IIsConnectionSettingsSavedUC {

    override suspend fun invoke() = settingsRepository.pointName?.isNotEmpty() ?: false
}