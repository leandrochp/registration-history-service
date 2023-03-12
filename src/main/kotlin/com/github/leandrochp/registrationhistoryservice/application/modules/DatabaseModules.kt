package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.configs.DatabaseConfig
import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import org.koin.dsl.module

val databaseModules = module {
    single {
        DatabaseConfig.connect(
            get<EnvironmentVariablesConfig>().databaseHost,
            get<EnvironmentVariablesConfig>().databasePort,
            get<EnvironmentVariablesConfig>().databaseName,
            get<EnvironmentVariablesConfig>().databaseAuthUser,
            get<EnvironmentVariablesConfig>().databaseAuthPassword
        )
    }
}
