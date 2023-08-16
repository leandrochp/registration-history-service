package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.application.config.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.config.configJsonMapper
import com.github.leandrochp.registrationhistoryservice.application.web.handler.FailureHandlerConfig
import com.github.leandrochp.registrationhistoryservice.application.web.route.RoutesConfig
import org.koin.dsl.module

val applicationModules = module {
    single { EnvironmentVariablesConfig() }
    single { RoutesConfig }
    single { FailureHandlerConfig }
    single { configJsonMapper() }
}
