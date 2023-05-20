package com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.extensions

import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.Address
import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.RegistryHistory
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities.RegistryHistoryEntity

fun RegistryHistoryEntity.toModel() =
    RegistryHistory(
        id = this.id,
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
