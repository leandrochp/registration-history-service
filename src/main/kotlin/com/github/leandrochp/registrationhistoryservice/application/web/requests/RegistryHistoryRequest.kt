package com.github.leandrochp.registrationhistoryservice.application.web.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistryHistoryRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    val age: Int,
    val email: String,
    val address: AddressRequest
)
