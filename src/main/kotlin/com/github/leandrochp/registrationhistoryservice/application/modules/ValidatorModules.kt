package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.application.web.requests.ValidateRequest
import com.github.leandrochp.registrationhistoryservice.application.web.validators.ValidateRequestValidator
import com.github.leandrochp.registrationhistoryservice.application.web.validators.Validator
import org.koin.dsl.module

val validatorModules = module {
    single<Validator<ValidateRequest>> {
        ValidateRequestValidator()
    }
}
