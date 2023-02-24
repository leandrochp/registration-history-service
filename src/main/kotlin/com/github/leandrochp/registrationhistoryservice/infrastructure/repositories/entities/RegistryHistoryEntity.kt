package com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities

data class RegistryHistoryEntity(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String,
    val address: AddressEntity
)
