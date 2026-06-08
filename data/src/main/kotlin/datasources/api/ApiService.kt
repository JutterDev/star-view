package datasources.api

import datacontracts.ApiRepository
import datasources.api.models.testconnection.TestConnectionMapper
import datasources.api.models.testconnection.TestConnectionRequest
import models.TestConnectionResult
import datacontracts.SettingsRepository

class ApiService(
    settingsRepository: SettingsRepository,
    private val testConnectionMapper: TestConnectionMapper,
    private val service: Service,
): ApiRepository {

    override suspend fun testConnection(key: String): TestConnectionResult {
        return testConnectionMapper.toDomain(
            service.post("test/test", TestConnectionRequest(key))
        )
    }
}