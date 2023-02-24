package com.github.leandrochp.registrationhistoryservice.application.web.validators

interface Validator<T> {

    fun validate(request: T): Map<String, List<String>>
}
