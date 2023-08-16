package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.domain.service.RegistryHistoryService
import com.github.leandrochp.registrationhistoryservice.domain.service.impl.RegistryHistoryServiceImpl
import org.koin.dsl.module

val serviceModules = module {
    single<RegistryHistoryService> {
        RegistryHistoryServiceImpl(get())
    }
}
