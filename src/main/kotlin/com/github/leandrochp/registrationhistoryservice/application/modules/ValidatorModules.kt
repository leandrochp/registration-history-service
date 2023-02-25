package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.application.web.validators.RegistryHistoryRequestValidator
import com.github.leandrochp.registrationhistoryservice.application.web.validators.Validator
import org.koin.dsl.module

val validatorModules = module {
    single<Validator<RegistryHistoryRequest>> {
        RegistryHistoryRequestValidator()
    }
}
