package com.github.leandrochp.registrationhistoryservice.application.web.extension

fun String.onlyNumbersAndLetters(): String {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}