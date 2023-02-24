package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.domain.services.RegistryHistoryService
import com.github.leandrochp.registrationhistoryservice.domain.services.impl.RegistryHistoryServiceImpl
import org.koin.dsl.module

val serviceModules = module {
    single<RegistryHistoryService> {
        RegistryHistoryServiceImpl(get())
    }
}
