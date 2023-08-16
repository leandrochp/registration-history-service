package com.github.leandrochp.registrationhistoryservice.application.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.leandrochp.registrationhistoryservice.application.web.extension.toModel
import com.github.leandrochp.registrationhistoryservice.application.web.request.RegistryHistoryRequest
import com.github.leandrochp.registrationhistoryservice.application.web.response.RegistryHistoryResponse
import com.github.leandrochp.registrationhistoryservice.application.web.validator.Validator
import com.github.leandrochp.registrationhistoryservice.domain.exception.InvalidRequestException
import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import com.github.leandrochp.registrationhistoryservice.domain.service.RegistryHistoryService

class RegistrationHistoryController(
    private val registryHistoryService: RegistryHistoryService,
    private val validator: Validator<RegistryHistoryRequest>,
    private val objectMapper: ObjectMapper
): LoggableClass() {

    fun save(requestHistoryRequest: RegistryHistoryRequest): RegistryHistoryResponse {
        logger.debug(
            "A new request to create a new registry history " +
                    "has been received: ${getJsonString(requestHistoryRequest)}"
        )
        validateRequest(requestHistoryRequest)

        return RegistryHistoryResponse(registryHistoryService.save(requestHistoryRequest.toModel())).also {
            logger.debug(
                "Replying the follow json response ${getJsonString(it)} in create registry history endpoint."
            )
        }
    }

    fun findAll(): List<RegistryHistoryResponse> {
        return registryHistoryService.findAll().map {
            RegistryHistoryResponse(it)
        }.also {
            logger.debug(
                "Replying the follow json response ${getJsonString(it)} in findAll endpoint."
            )
        }

    }

    fun findById(id: String): RegistryHistoryResponse {
        return RegistryHistoryResponse(registryHistoryService.findById(id)).also {
            logger.debug(
                "Replying the follow json response ${getJsonString(it)} in findById endpoint."
            )
        }
    }

    fun findByFirstName(firstName: String): List<RegistryHistoryResponse> {
        return registryHistoryService.findByFirstName(firstName).map {
            RegistryHistoryResponse(it)
        }.also {
            logger.debug(
                "Replying the follow json response ${getJsonString(it)} in findByFirstName endpoint."
            )
        }
    }

    fun findByLastName(lastName: String): List<RegistryHistoryResponse> {
        return registryHistoryService.findByLastName(lastName).map {
            RegistryHistoryResponse(it)
        }.also {
            logger.debug(
                "Replying the follow json response ${getJsonString(it)} in findByLastName endpoint."
            )
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

    private fun getJsonString(any: Any): String = objectMapper.writeValueAsString(any)

}
