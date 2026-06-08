package datacontracts

import models.catalog.CatalogObject

interface CatalogRepository {

    suspend fun getAllItems(): List<CatalogObject>
}