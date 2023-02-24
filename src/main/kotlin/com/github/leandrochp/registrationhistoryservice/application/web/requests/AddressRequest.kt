package com.github.leandrochp.registrationhistoryservice.application.web.requests

data class AddressRequest(
    val street: String,
    val city: String,
    val code: String
)
