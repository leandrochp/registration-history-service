package com.github.leandrochp.registrationhistoryservice.application.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun configJsonMapper(): ObjectMapper =
    jacksonObjectMapper().apply {
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        this.registerKotlinModule()
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        this.propertyNamingStrategy = SNAKE_CASE
    }
