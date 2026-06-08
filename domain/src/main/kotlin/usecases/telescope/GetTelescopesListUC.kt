package usecases.telescope

import datacontracts.ApiRepository
import models.telescope.TelescopeInfo

interface IGetTelescopesListUC {

    suspend operator fun invoke(): List<TelescopeInfo>
}

class GetTelescopesListUC(
    private val apiRepository: ApiRepository,
): IGetTelescopesListUC {

    override suspend fun invoke() = apiRepository.telescopeList()
}