package com.github.leandrochp.registrationhistoryservice.domain.services.impl

import com.github.leandrochp.registrationhistoryservice.domain.exceptions.RegistryHistoryAlreadyRegisteredException
import com.github.leandrochp.registrationhistoryservice.domain.exceptions.RegistryHistoryNotFoundException
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.domain.services.RegistryHistoryService

class RegistryHistoryServiceImpl(
    private val registryHistoryRepository: RegistryHistoryRepository
) : RegistryHistoryService {

    override fun save(registryHistory: RegistryHistory): RegistryHistory {
        registryHistoryRepository.findByDocument(registryHistory.document)?.run {
            throw RegistryHistoryAlreadyRegisteredException(
                "There is a registry history with the follow document: [${registryHistory.document}] already registered."
            )
        }
        return registryHistoryRepository.save(registryHistory)
    }

    override fun findAll(): List<RegistryHistory> {
        registryHistoryRepository.findAll().run {
            if (isEmpty())
                throw RegistryHistoryNotFoundException("There were not found registry histories in the database.")
            return this
        }
    }

    override fun findById(id: String): RegistryHistory {
        return registryHistoryRepository.findById(id)
            ?: throw RegistryHistoryNotFoundException("The registry history with the id: [$id] not found.")
    }

    override fun findByFirstName(firstName: String): List<RegistryHistory> {
        registryHistoryRepository.findByFirstName(firstName).run {
            if (isEmpty())
                throw RegistryHistoryNotFoundException("There were not found registry histories " +
                        "with the first name: [$firstName].")
            return this
        }
    }

    override fun findByLastName(lastName: String): List<RegistryHistory> {
        registryHistoryRepository.findByLastName(lastName).run {
            if (isEmpty())
                throw RegistryHistoryNotFoundException("There were not found registry histories " +
                        "with the first name: [$lastName].")
            return this
        }
    }

}