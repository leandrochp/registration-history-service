package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.configs.configJsonMapper
import com.github.leandrochp.registrationhistoryservice.application.web.handlers.FailureHandlerConfig
import com.github.leandrochp.registrationhistoryservice.application.web.routes.RoutesConfig
import org.koin.dsl.module

val applicationModules = module {
    single { EnvironmentVariablesConfig() }
    single { RoutesConfig }
    single { FailureHandlerConfig }
    single { configJsonMapper() }
}
