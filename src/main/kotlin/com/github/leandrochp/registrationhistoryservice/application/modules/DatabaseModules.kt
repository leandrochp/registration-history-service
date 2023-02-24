package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.configs.DatabaseConfig
import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.configs.configJsonMapper
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.MongoDbRegistryHistoryRepository
import org.koin.dsl.module

val databaseModules = module {

    single {
        DatabaseConfig.connect(
            get<EnvironmentVariablesConfig>().databaseHost,
            get<EnvironmentVariablesConfig>().databaseName,
            get<EnvironmentVariablesConfig>().databaseAuthUser,
            get<EnvironmentVariablesConfig>().databaseAuthPassword
        )
    }

    single<RegistryHistoryRepository> {
        MongoDbRegistryHistoryRepository(get(), configJsonMapper())
    }

}
