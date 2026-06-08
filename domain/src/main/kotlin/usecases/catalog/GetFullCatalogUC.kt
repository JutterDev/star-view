package usecases.catalog

import datacontracts.CatalogRepository
import datacontracts.SettingsRepository
import models.ServerInfo
import models.catalog.CatalogObject

interface IGetFullCatalogUC {

    suspend operator fun invoke(): List<CatalogObject>
}

class GetFullCatalogUC(
    private val catalogRepository: CatalogRepository,
): IGetFullCatalogUC {

    override suspend fun invoke() = catalogRepository.getAllItems()
}