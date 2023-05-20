package com.github.leandrochp.registrationhistoryservice.domain.registrationhistory

import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.Address
import com.github.leandrochp.registrationhistoryservice.domain.registrationhistory.RegistryHistory

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