package com.github.leandrochp.registrationhistoryservice.domain.services

import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory

interface RegistryHistoryService {
    fun save(registryHistory: RegistryHistory): RegistryHistory
    fun findAll(): List<RegistryHistory>
    fun findById(id: String): RegistryHistory
    fun findByFirstName(firstName: String): List<RegistryHistory>
    fun findByLastName(lastName: String): List<RegistryHistory>
}
