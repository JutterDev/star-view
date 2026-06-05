package online.jutter.di

import online.jutter.datacontracts.SettingsRepository
import datasources.settings.FileSettingsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val provideDataModule = module {

    singleOf(::FileSettingsRepository).bind(SettingsRepository::class)

}