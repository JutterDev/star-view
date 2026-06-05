package di

import usecases.serverconnection.ISaveServerConnectionSettingsUC
import usecases.serverconnection.SaveServerConnectionSettingsUC
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import usecases.serverconnection.ClearServerConnectionSettingsUC
import usecases.serverconnection.IClearServerConnectionSettingsUC
import usecases.serverconnection.IIsConnectionSettingsSavedUC
import usecases.serverconnection.IsConnectionSettingsSavedUC

val provideDomainModule = module {
    singleOf(::SaveServerConnectionSettingsUC).bind(ISaveServerConnectionSettingsUC::class)
    singleOf(::ClearServerConnectionSettingsUC).bind(IClearServerConnectionSettingsUC::class)
    singleOf(::IsConnectionSettingsSavedUC).bind(IIsConnectionSettingsSavedUC::class)
}