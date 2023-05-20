package com.github.leandrochp.registrationhistoryservice.domain.registrationhistory

data class RegistryHistory(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val document: String,
    val age: Int,
    val email: String,
    val address: Address
)
