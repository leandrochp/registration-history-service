package com.github.leandrochp.registrationhistoryservice.domain.services

import com.github.leandrochp.registrationhistoryservice.domain.exceptions.RegistryHistoryAlreadyRegisteredException
import com.github.leandrochp.registrationhistoryservice.domain.exceptions.RegistryHistoryNotFoundException
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.domain.services.impl.RegistryHistoryServiceImpl
import com.github.leandrochp.registrationhistoryservice.utils.mocks.RegistryHistoryMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class RegistryHistoryServiceTest {

    private val repositoryMock = mockk<RegistryHistoryRepository>()
    private val service = RegistryHistoryServiceImpl(repositoryMock)

    private val registryHistoryMock = RegistryHistoryMock.sample()

    @Test
    fun `should create a new registry history when there is not a registry with the same document already registered`() {
        every { repositoryMock.findByDocument(registryHistoryMock.document) } returns null
        every { repositoryMock.save(registryHistoryMock) } returns registryHistoryMock

        assertDoesNotThrow {
            service.save(registryHistoryMock)
        }

        verify { repositoryMock.save(registryHistoryMock) }
    }

    @Test
    fun `should not create a new registry history when there is a registry with the same document already registered`() {
        every { repositoryMock.findByDocument(registryHistoryMock.document) } returns registryHistoryMock

        assertThrows<RegistryHistoryAlreadyRegisteredException>(
            message = "There is a registry history with the follow document: [${registryHistoryMock.document}] already registered."
        ) {
            service.save(registryHistoryMock)
        }

        verify { repositoryMock.findByDocument(registryHistoryMock.document) }
        verify(exactly = 0) { repositoryMock.save(registryHistoryMock) }
    }

    @Test
    fun `should return an existent registry history when the id informed`() {
        val id = "123"
        every { repositoryMock.findById(id) } returns registryHistoryMock

        assertDoesNotThrow {
            service.findById(id)
        }

        verify { repositoryMock.findById(id) }
    }

    @Test
    fun `should throws an exception when there is not a registry history when the id informed`() {
        val id = "123"
        every { repositoryMock.findById(id) } returns null

        assertThrows<RegistryHistoryNotFoundException>(
            message = "The registry history with the id: [$id] not found."
        ) {
            service.findById(id)
        }

        verify { repositoryMock.findById(id) }
    }

    @Test
    fun `should return an list registry histories when the first name exists on database`() {
        val firstName = "John"
        val registryHistoriesMock = listOf(registryHistoryMock)
        every { repositoryMock.findByFirstName(firstName) } returns registryHistoriesMock

        val registryHistories = assertDoesNotThrow {
            service.findByFirstName(firstName)
        }

        verify { repositoryMock.findByFirstName(firstName) }
        Assertions.assertThat(registryHistories).isNotEmpty
    }

    @Test
    fun `should throws an exception when there is not registry histories when the first name exists on database`() {
        val firstName = "John"
        every { repositoryMock.findByFirstName(firstName) } returns emptyList()

        assertThrows<RegistryHistoryNotFoundException>(
            message = "There were not found registry histories with the first name: [$firstName]."
        ) {
            service.findByFirstName(firstName)
        }

        verify { repositoryMock.findByFirstName(firstName) }
    }

    @Test
    fun `should return an list registry histories when the last name exists on database`() {
        val lastName = "Wick"
        val registryHistoriesMock = listOf(registryHistoryMock)
        every { repositoryMock.findByLastName(lastName) } returns registryHistoriesMock

        val registryHistories = assertDoesNotThrow {
            service.findByLastName(lastName)
        }

        verify { repositoryMock.findByLastName(lastName) }
        Assertions.assertThat(registryHistories).isNotEmpty
    }

    @Test
    fun `should throws an exception when there is not registry histories when the last name exists on database`() {
        val lastName = "Wick"
        every { repositoryMock.findByLastName(lastName) } returns emptyList()

        assertThrows<RegistryHistoryNotFoundException>(
            message = "There were not found registry histories with the last name: [$lastName]."
        ) {
            service.findByLastName(lastName)
        }

        verify { repositoryMock.findByLastName(lastName) }
    }

    @Test
    fun `should return an list all registry histories exists on database`() {
        val registryHistoriesMock = listOf(registryHistoryMock)
        every { repositoryMock.findAll() } returns registryHistoriesMock

        val registryHistories = assertDoesNotThrow {
            service.findAll()
        }

        verify { repositoryMock.findAll() }
        Assertions.assertThat(registryHistories).isNotEmpty
    }

    @Test
    fun `should throws an exception when there is not registry histories exists on database`() {
        every { repositoryMock.findAll() } returns emptyList()

        assertThrows<RegistryHistoryNotFoundException>(
            message = "There were not found registry histories in the database."
        ) {
            service.findAll()
        }

        verify { repositoryMock.findAll() }
    }

}
