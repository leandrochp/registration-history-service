package com.github.leandrochp.registrationhistoryservice.domain.exception

class RegistryHistoryNotFoundException(
  message: String
) : ApiException(message = message) {

  override fun httpStatus(): Int = HttpStatus.NOT_FOUND.statusCode

  override fun apiError(): ApiError = ApiError.REGISTRY_HISTORY_NOT_FOUND

  override fun userResponseMessage(): String = "$message"

  override fun details() = emptyMap<String, List<Any>>()
}
