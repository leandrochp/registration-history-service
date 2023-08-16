package com.github.leandrochp.registrationhistoryservice.application.web.request

data class AddressRequest(
    val street: String,
    val city: String,
    val code: String
)
