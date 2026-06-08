package datacontracts

import models.TestConnectionResult

interface ApiRepository {

    suspend fun testConnection(key: String): TestConnectionResult
}