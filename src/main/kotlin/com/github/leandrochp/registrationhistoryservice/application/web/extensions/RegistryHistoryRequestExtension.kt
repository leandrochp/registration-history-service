package com.github.leandrochp.registrationhistoryservice.application.web.extensions

import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.Address
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory

fun RegistryHistoryRequest.toModel() =
    RegistryHistory(
        firstName = this.firstName,
        lastName = this.lastName,
        age = this.age,
        email = this.email,
        address = Address(
            street = this.address.street,
            city = this.address.city,
            code = this.address.code
        )
    )
