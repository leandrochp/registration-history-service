package com.github.leandrochp.registrationhistoryservice.application.web.controllers

import com.github.leandrochp.registrationhistoryservice.application.web.extensions.toModel
import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.application.web.responses.RegistryHistoryResponse
import com.github.leandrochp.registrationhistoryservice.application.web.validators.Validator
import com.github.leandrochp.registrationhistoryservice.domain.exceptions.InvalidRequestException
import com.github.leandrochp.registrationhistoryservice.domain.services.RegistryHistoryService

class RegistrationHistoryController(
    private val registryHistoryService: RegistryHistoryService,
    private val validator: Validator<RegistryHistoryRequest>
) {

    fun save(request: RegistryHistoryRequest): RegistryHistoryResponse {
        //logger.info("Saving registry history: $registry")
        validateRequest(request)

        return RegistryHistoryResponse(registryHistoryService.save(request.toModel()))
    }

    fun findAll(): List<RegistryHistoryResponse> {
        //logger.info("Finding all registry histories")
        return registryHistoryService.findAll().map {
            RegistryHistoryResponse(it)
        }
    }

    fun findById(id: String): RegistryHistoryResponse {
        //logger.info("Finding registry history with id: $id")
        return RegistryHistoryResponse(registryHistoryService.findById(id))
    }

    fun findByFirstName(firstName: String): List<RegistryHistoryResponse> {
        //logger.info("Finding registry history with id: $id")
        return registryHistoryService.findByFirstName(firstName).map {
            RegistryHistoryResponse(it)
        }
    }

    fun findByLastName(lastName: String): List<RegistryHistoryResponse> {
        //logger.info("Finding registry history with id: $id")
        return registryHistoryService.findByLastName(lastName).map {
            RegistryHistoryResponse(it)
        }
    }

    private fun validateRequest(request: RegistryHistoryRequest) {
        val errors = validator.validate(request)

        if (errors.isNotEmpty()) {
            val message = "There are some invalids fields in request."

            throw InvalidRequestException(
                message = message,
                details = errors
            )
        }
    }

}
