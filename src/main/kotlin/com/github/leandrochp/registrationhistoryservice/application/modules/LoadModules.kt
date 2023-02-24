package com.github.leandrochp.registrationhistoryservice.application.modules

import org.koin.core.context.startKoin

fun loadModules() {
    startKoin {
        modules(
            applicationModules,
            databaseModules,
            controllerModules,
            serviceModules,
            validatorModules
        )
    }
}