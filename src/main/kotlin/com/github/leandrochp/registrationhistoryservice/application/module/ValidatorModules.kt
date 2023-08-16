package com.github.leandrochp.registrationhistoryservice.application.module

import com.github.leandrochp.registrationhistoryservice.application.web.request.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.application.web.validator.RegistryHistoryRequestValidator
import com.github.leandrochp.registrationhistoryservice.application.web.validator.Validator
import org.koin.dsl.module

val validatorModules = module {
    single<Validator<RegistryHistoryRequest>> {
        RegistryHistoryRequestValidator()
    }
}
