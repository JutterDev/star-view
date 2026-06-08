package di

import usecases.serverconnection.ISaveServerConnectionSettingsUC
import usecases.serverconnection.SaveServerConnectionSettingsUC
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import usecases.catalog.GetFullCatalogUC
import usecases.catalog.IGetFullCatalogUC
import usecases.serverconnection.ClearServerConnectionSettingsUC
import usecases.serverconnection.GetServerInfoUC
import usecases.serverconnection.IClearServerConnectionSettingsUC
import usecases.serverconnection.IGetServerInfoUC
import usecases.serverconnection.IIsConnectionSettingsSavedUC
import usecases.serverconnection.ITestConnectionUC
import usecases.serverconnection.IsConnectionSettingsSavedUC
import usecases.serverconnection.TestConnectionUC

val provideDomainModule = module {
    singleOf(::SaveServerConnectionSettingsUC).bind(ISaveServerConnectionSettingsUC::class)
    singleOf(::ClearServerConnectionSettingsUC).bind(IClearServerConnectionSettingsUC::class)
    singleOf(::IsConnectionSettingsSavedUC).bind(IIsConnectionSettingsSavedUC::class)
    singleOf(::TestConnectionUC).bind(ITestConnectionUC::class)
    singleOf(::GetServerInfoUC).bind(IGetServerInfoUC::class)
    singleOf(::GetFullCatalogUC).bind(IGetFullCatalogUC::class)
}