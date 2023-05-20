package com.github.leandrochp.registrationhistoryservice.application.web.extensions

import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.Address
import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.RegistryHistory

fun RegistryHistoryRequest.toModel() =
    RegistryHistory(
        firstName = this.firstName,
        lastName = this.lastName,
        document = this.document,
        age = this.age,
        email = this.email,
        address = Address(
            street = this.address.street,
            city = this.address.city,
            code = this.address.code
        )
    )
