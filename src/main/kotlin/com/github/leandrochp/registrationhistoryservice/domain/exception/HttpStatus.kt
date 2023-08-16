package com.github.leandrochp.registrationhistoryservice.domain.exception

enum class HttpStatus(val statusCode: Int) {
    BAD_REQUEST(400),
    NOT_FOUND(404)
}
