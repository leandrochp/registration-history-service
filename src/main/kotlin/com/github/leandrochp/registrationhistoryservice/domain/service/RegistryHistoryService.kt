package com.github.leandrochp.registrationhistoryservice.domain.service

import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.RegistryHistory

interface RegistryHistoryService {
    fun save(registryHistory: RegistryHistory): RegistryHistory
    fun findAll(): List<RegistryHistory>
    fun findById(id: String): RegistryHistory
    fun findByFirstName(firstName: String): List<RegistryHistory>
    fun findByLastName(lastName: String): List<RegistryHistory>
}
