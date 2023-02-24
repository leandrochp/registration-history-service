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
        val registryHistories = registryHistoryRepository.findAll()
        if (registryHistories.isEmpty())
            throw RegistryHistoryNotFoundException("Not were found registry histories in the database.")
        return registryHistories
    }

    override fun findById(id: String): RegistryHistory {
        return registryHistoryRepository.findById(id)
            ?: throw RegistryHistoryNotFoundException("The registry history with the id: $id not found.")
    }

    override fun findByFirstName(firstName: String): List<RegistryHistory> {
        val registryHistories = registryHistoryRepository.findByFirstName(firstName)
        if (registryHistories.isEmpty())
            throw RegistryHistoryNotFoundException("The registry histories with the first name: $firstName not found.")
        return registryHistories
    }

    override fun findByLastName(lastName: String): List<RegistryHistory> {
        val registryHistories = registryHistoryRepository.findByLastName(lastName)
        if (registryHistories.isEmpty())
            throw RegistryHistoryNotFoundException("The registry histories with the last name: $lastName not found.")
        return registryHistories
    }

}