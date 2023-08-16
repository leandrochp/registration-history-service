package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.application.web.controller.HealthCheckController
import com.github.leandrochp.registrationhistoryservice.application.web.controller.RegistrationHistoryController
import org.koin.dsl.module

val controllerModules = module {
    single {
        RegistrationHistoryController(get(), get(), get())
    }

    single { HealthCheckController() }
}
