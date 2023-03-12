package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.configs.configJsonMapper
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.MongoDbRegistryHistoryRepository
import org.koin.dsl.module

val repositoryModules = module {
    single<RegistryHistoryRepository> {
        MongoDbRegistryHistoryRepository(
            get(),
            get<EnvironmentVariablesConfig>().databaseName,
            configJsonMapper()
        )
    }
}
