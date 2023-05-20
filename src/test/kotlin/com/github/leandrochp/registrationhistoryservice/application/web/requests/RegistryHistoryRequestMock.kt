package com.github.leandrochp.registrationhistoryservice.application.web.requests

import com.github.leandrochp.registrationhistoryservice.application.web.requests.AddressRequest
import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest

object RegistryHistoryRequestMock {

    fun sample() = RegistryHistoryRequest(
        firstName = "John",
        lastName = "Wick",
        document = "548.029.660-40",
        age = 44,
        email = "babayaga@hotelcontinental.com",
        address = AddressRequest(
            street = "121 Mill Neck",
            city = "Long Island",
            code = "11765"
        )
    )
}