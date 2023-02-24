package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.web.controllers.RegistrationHistoryController
import org.koin.dsl.module

val controllerModules = module {
    single { RegistrationHistoryController(get(), get()) }
}
