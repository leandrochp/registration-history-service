package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.application.config.DatabaseConfig
import com.github.leandrochp.registrationhistoryservice.application.config.EnvironmentVariablesConfig
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
