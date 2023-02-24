package com.github.leandrochp.registrationhistoryservice.domain.registryhistory

data class RegistryHistory(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val address: Address
)
