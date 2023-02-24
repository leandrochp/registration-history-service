package com.github.leandrochp.registrationhistoryservice.domain.services.impl

import com.github.leandrochp.registrationhistoryservice.domain.exceptions.RegistryHistoryNotFoundException
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.domain.services.RegistryHistoryService

class RegistryHistoryServiceImpl(
    private val registryHistoryRepository: RegistryHistoryRepository
) : RegistryHistoryService {

    override fun save(registryHistory: RegistryHistory): RegistryHistory {
        return registryHistoryRepository.save(registryHistory)
    }

    override fun findAll(): List<RegistryHistory> {
        return registryHistoryRepository.findAll()
    }

    override fun findById(id: String): RegistryHistory {
        return registryHistoryRepository.findById(id)
            ?: throw RegistryHistoryNotFoundException("The registry history with the id: $id not found.")
    }

    override fun findByFirstName(firstName: String): List<RegistryHistory> {
        return registryHistoryRepository.findByFirstName(firstName)
    }

    override fun findByLastName(lastName: String): List<RegistryHistory> {
        return registryHistoryRepository.findByLastName(lastName)
    }


}
