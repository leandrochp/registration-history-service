package com.github.leandrochp.registrationhistoryservice.application.web.validator

data class Validation<T>(
    val fieldName: String,
    val fieldValue: T,
    val errorMessageList: MutableList<String> = mutableListOf()
)

