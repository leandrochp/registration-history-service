package com.github.leandrochp.registrationhistoryservice.application.web.responses

data class HealthCheckResponse(
    val status: String,
    val up: Boolean
)
