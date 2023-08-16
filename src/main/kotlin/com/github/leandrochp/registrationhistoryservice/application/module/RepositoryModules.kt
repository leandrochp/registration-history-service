package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.application.config.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.config.configJsonMapper
import com.github.leandrochp.registrationhistoryservice.domain.repository.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.infrastructure.repository.MongoDbRegistryHistoryRepository
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
