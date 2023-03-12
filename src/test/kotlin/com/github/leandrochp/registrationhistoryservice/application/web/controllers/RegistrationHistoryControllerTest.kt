package com.github.leandrochp.registrationhistoryservice.application.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.leandrochp.registrationhistoryservice.application.web.validators.RegistryHistoryRequestValidator
import com.github.leandrochp.registrationhistoryservice.domain.exceptions.InvalidRequestException
import com.github.leandrochp.registrationhistoryservice.domain.services.RegistryHistoryService
import com.github.leandrochp.registrationhistoryservice.utils.mocks.RegistryHistoryMock
import com.github.leandrochp.registrationhistoryservice.utils.mocks.RegistryHistoryRequestMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RegistrationHistoryControllerTest {

    private val registryHistoryServiceMock = mockk<RegistryHistoryService>()
    private val registryRequestValidatorMock = mockk<RegistryHistoryRequestValidator>()
    private val objectMapperMock = mockk<ObjectMapper>(relaxed = true)

    private val registrationHistoryController = RegistrationHistoryController(
        registryHistoryServiceMock,
        registryRequestValidatorMock,
        objectMapperMock
    )

    private val registryHistoryMock = RegistryHistoryMock.sample()
    private val registryHistoryRequestMock = RegistryHistoryRequestMock.sample()

    @Test
    fun `should return Created status code when the request is valid`() {
        every { registryRequestValidatorMock.validate(any()) } returns emptyMap()
        every { registryHistoryServiceMock.save(any()) } returns registryHistoryMock

        val response = assertDoesNotThrow {
            registrationHistoryController.save(registryHistoryRequestMock)
        }

        verify { registryRequestValidatorMock.validate(any()) }
        verify { registryHistoryServiceMock.save(any()) }

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(registryHistoryMock.id)
        assertThat(response.firstName).isEqualTo(registryHistoryMock.firstName)
        assertThat(response.lastName).isEqualTo(registryHistoryMock.lastName)
        assertThat(response.document).isEqualTo(registryHistoryMock.document)
        assertThat(response.age).isEqualTo(registryHistoryMock.age)
        assertThat(response.email).isEqualTo(registryHistoryMock.email)
        assertThat(response.address).isNotNull
        assertThat(response.address.street).isEqualTo(registryHistoryMock.address.street)
        assertThat(response.address.city).isEqualTo(registryHistoryMock.address.city)
        assertThat(response.address.code).isEqualTo(registryHistoryMock.address.code)
    }

    @Test
    fun `should throws an exception when there is any invalid field on request`() {
        every { registryRequestValidatorMock.validate(any()) } returns mapOf("test" to listOf("test"))

        assertThrows<InvalidRequestException>(
            message = "There are some invalids fields in request."
        ) {
            registrationHistoryController.save(registryHistoryRequestMock)
        }

        verify { registryRequestValidatorMock.validate(any()) }
        verify(exactly = 0) { registryHistoryServiceMock.save(any()) }
    }

    @Test
    fun `should return Ok status code when there is a registry history already registered with the id informed`() {
        val id = "200"

        every { registryHistoryServiceMock.findById(any()) } returns registryHistoryMock

        val response = assertDoesNotThrow {
            registrationHistoryController.findById(id)
        }

        verify { registryHistoryServiceMock.findById(id) }

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(registryHistoryMock.id)
        assertThat(response.firstName).isEqualTo(registryHistoryMock.firstName)
        assertThat(response.lastName).isEqualTo(registryHistoryMock.lastName)
        assertThat(response.document).isEqualTo(registryHistoryMock.document)
        assertThat(response.age).isEqualTo(registryHistoryMock.age)
        assertThat(response.email).isEqualTo(registryHistoryMock.email)
        assertThat(response.address).isNotNull
        assertThat(response.address.street).isEqualTo(registryHistoryMock.address.street)
        assertThat(response.address.city).isEqualTo(registryHistoryMock.address.city)
        assertThat(response.address.code).isEqualTo(registryHistoryMock.address.code)
    }

}