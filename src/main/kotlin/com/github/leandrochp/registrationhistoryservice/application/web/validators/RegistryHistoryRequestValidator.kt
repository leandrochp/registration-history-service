package com.github.leandrochp.registrationhistoryservice.application.web.validators

import com.github.leandrochp.registrationhistoryservice.application.web.extensions.isInvalidCPF
import com.github.leandrochp.registrationhistoryservice.application.web.extensions.isNull
import com.github.leandrochp.registrationhistoryservice.application.web.extensions.isNullOrBlank
import com.github.leandrochp.registrationhistoryservice.application.web.extensions.isNullOrNegative
import com.github.leandrochp.registrationhistoryservice.application.web.requests.RegistryHistoryRequest
import org.koin.core.component.KoinComponent

class RegistryHistoryRequestValidator : KoinComponent, Validator<RegistryHistoryRequest> {

    override fun validate(request: RegistryHistoryRequest): Map<String, List<String>> {

        val errorList = mutableListOf<Validation<*>>()

        with(request) {
            errorList.add(Validation("first_name", this.firstName).isNullOrBlank())
            errorList.add(Validation("last_name", this.lastName).isNullOrBlank())
            errorList.add(Validation("document", this.document).isNullOrBlank())
            errorList.add(Validation("document", this.document).isInvalidCPF())
            errorList.add(Validation("age", this.age).isNullOrNegative())
            errorList.add(Validation("email", this.email).isNullOrBlank())
            errorList.add(Validation("address", this.address).isNull())
            errorList.add(Validation("street", this.address.street).isNullOrBlank())
            errorList.add(Validation("city", this.address.city).isNullOrBlank())
            errorList.add(Validation("code", this.address.code).isNullOrBlank())
        }

        return errorList.filter { it.errorMessageList.isNotEmpty() }
            .groupBy { it.fieldName }
            .mapValues { it.value.flatMap { valueItem -> valueItem.errorMessageList } }
    }
}
