package com.github.leandrochp.registrationhistoryservice.application.web.validator

interface Validator<T> {

    fun validate(request: T): Map<String, List<String>>
}
