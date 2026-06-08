package usecases.serverconnection

import datacontracts.SettingsRepository
import models.ServerInfo

interface IGetServerInfoUC {

    suspend operator fun invoke(): ServerInfo
}

class GetServerInfoUC(
    private val settingsRepository: SettingsRepository,
): IGetServerInfoUC {

    override suspend fun invoke() = ServerInfo(
        ip = settingsRepository.ip ?: "-",
        port = settingsRepository.port ?: "-",
        pointName = settingsRepository.pointName ?: "-"
    )
}