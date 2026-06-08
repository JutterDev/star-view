package datacontracts

import models.TestConnectionResult
import models.telescope.TelescopeInfo

interface ApiRepository {

    suspend fun testConnection(key: String): TestConnectionResult

    suspend fun telescopeList(): List<TelescopeInfo>
}