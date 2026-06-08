package di

import datacontracts.ApiRepository
import datacontracts.CatalogRepository
import datasources.api.ApiService
import datasources.api.models.testconnection.TestConnectionMapper
import datacontracts.SettingsRepository
import datasources.api.Service
import datasources.api.models.telescope.TelescopeInfoMapper
import datasources.catalog.CsvCatalogRepository
import datasources.settings.FileSettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val provideDataModule = module {

    singleOf(::FileSettingsRepository).bind(SettingsRepository::class)

    singleOf(::ApiService).bind(ApiRepository::class)
    singleOf(::Service)

    singleOf(::TestConnectionMapper)
    singleOf(::TelescopeInfoMapper)

    singleOf(::CsvCatalogRepository).bind(CatalogRepository::class)
}