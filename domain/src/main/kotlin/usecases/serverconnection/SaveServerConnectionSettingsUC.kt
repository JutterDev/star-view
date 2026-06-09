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

        val oldPort = settingsRepository.port
        val oldIp = settingsRepository.ip

        settingsRepository.ip = ip
        settingsRepository.port = port

        try {
            val connectionInfo = testConnectionUC.invoke(key)
            settingsRepository.pointName = connectionInfo.pointName
        } catch (ex: Exception) {
            settingsRepository.ip = oldIp
            settingsRepository.port = oldPort
            throw ex
        }

        settingsRepository.key = key
    }
}