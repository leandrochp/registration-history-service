package com.github.leandrochp.registrationhistoryservice.application.web.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistryHistoryRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    val document: String,
    val age: Int,
    val email: String,
    val address: AddressRequest
)
