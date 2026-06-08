package usecases.serverconnection

import datacontracts.ApiRepository
import models.TestConnectionResult

interface ITestConnectionUC {

    suspend operator fun invoke(key: String): TestConnectionResult
}

class TestConnectionUC(
    private val apiRepository: ApiRepository,
): ITestConnectionUC {

    override suspend fun invoke(key: String) = apiRepository.testConnection(key)
}