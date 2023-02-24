package com.github.leandrochp.registrationhistoryservice.application.web.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory

data class RegistryHistoryResponse(
    val id: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    val age: Int,
    val email: String,
    val address: AddressResponse
) {

    constructor(registryHistory: RegistryHistory) : this(
        id = registryHistory.id!!,
        firstName = registryHistory.firstName,
        lastName = registryHistory.lastName,
        age = registryHistory.age,
        email = registryHistory.email,
        address = AddressResponse(
            street = registryHistory.address.street,
            city = registryHistory.address.city,
            code = registryHistory.address.code
        )
    )
}