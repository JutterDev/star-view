package usecases.serverconnection

import datacontracts.SettingsRepository

interface ISaveServerConnectionSettingsUC {

    suspend operator fun invoke(ip: String, port: String, key: String)
}

class SaveServerConnectionSettingsUC(
    private val settingsRepository: SettingsRepository,
    private val testConnectionUC: ITestConnectionUC,
): ISaveServerConnectionSettingsUC {

    override suspend fun invoke(ip: String, port: String, key: String) {
        settingsRepository.ip = ip
        settingsRepository.port = port

        val connectionInfo = testConnectionUC.invoke(key)
        settingsRepository.pointName = connectionInfo.pointName

        settingsRepository.key = key
    }
}