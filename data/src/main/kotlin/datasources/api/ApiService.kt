package datasources.api

import datacontracts.ApiRepository
import datasources.api.models.testconnection.TestConnectionMapper
import datasources.api.models.testconnection.TestConnectionRequest
import models.TestConnectionResult
import datacontracts.SettingsRepository
import datasources.api.models.telescope.TelescopeInfoMapper
import models.telescope.TelescopeInfo

class ApiService(
    settingsRepository: SettingsRepository,
    private val testConnectionMapper: TestConnectionMapper,
    private val telescopeInfoMapper: TelescopeInfoMapper,
    private val service: Service,
): ApiRepository {

    override suspend fun testConnection(key: String): TestConnectionResult {
        return testConnectionMapper.toDomain(
            service.post("test/test", TestConnectionRequest(key))
        )
    }

    override suspend fun telescopeList(): List<TelescopeInfo> {
        return telescopeInfoMapper.toDomain(
            service.get("telescope/list")
        )
    }
}