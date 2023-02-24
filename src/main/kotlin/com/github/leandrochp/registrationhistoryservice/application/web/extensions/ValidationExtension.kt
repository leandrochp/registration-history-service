package com.github.leandrochp.registrationhistoryservice.application.web.extensions

import com.github.leandrochp.registrationhistoryservice.application.web.validators.Validation

fun <T> Validation<T>.isNull(): Validation<T> {
    if (this.fieldValue == null) {
        errorMessageList.add("must not be null.")
    }
    return this
}

fun Validation<String>.isNullOrBlank(): Validation<String> {
    this.fieldValue.takeIf { it.isBlank() }?.run {
        errorMessageList.add("must not be empty or null.")
    }
    return this
}

fun Validation<Int>.isNullOrNegative(): Validation<Int> {
    val zero = 0
    this.fieldValue.takeIf { it < zero }?.run {
        errorMessageList.add("must not be null or a negative number.")
    }
    return this
}
