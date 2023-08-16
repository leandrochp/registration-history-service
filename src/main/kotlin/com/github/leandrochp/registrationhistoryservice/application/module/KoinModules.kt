package com.github.leandrochp.registrationhistoryservice.application.module

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

object KoinModules {

    fun start() = startKoin {
        modules(
            applicationModules,
            databaseModules,
            repositoryModules,
            controllerModules,
            serviceModules,
            validatorModules
        )
    }

    fun stop() = stopKoin()
}