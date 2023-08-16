package com.github.leandrochp.registrationhistoryservice.infrastructure.repository.entity

data class RegistryHistoryEntity(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val document: String,
    val age: Int,
    val email: String,
    val address: AddressEntity
)
