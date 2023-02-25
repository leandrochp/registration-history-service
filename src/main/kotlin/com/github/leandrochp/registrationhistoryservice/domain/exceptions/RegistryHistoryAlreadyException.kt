package com.github.leandrochp.registrationhistoryservice.domain.exceptions

class RegistryHistoryAlreadyRegisteredException(
    message: String
) : ApiException(message = message) {

    override fun httpStatus() = HttpStatus.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.REGISTRY_HISTORY_REGISTERED

    override fun userResponseMessage() = "$message"

    override fun details() = emptyMap<String, List<Any>>()
}