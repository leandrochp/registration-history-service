package com.github.leandrochp.registrationhistoryservice.utils.mocks

import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.Address
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory

object RegistryHistoryMock {

    fun sample() = RegistryHistory(
        id = "123",
        firstName = "John",
        lastName = "Wick",
        document = "548.029.660-40",
        age = 44,
        email = "babayaga@hotelcontinental.com",
        address = Address(
            street = "121 Mill Neck",
            city = "Long Island",
            code = "11765"
        )
    )
}