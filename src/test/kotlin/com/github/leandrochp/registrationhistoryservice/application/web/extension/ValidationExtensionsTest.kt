package com.github.leandrochp.registrationhistoryservice.application.web.extension

import com.github.leandrochp.registrationhistoryservice.application.web.validator.Validation
import com.github.leandrochp.registrationhistoryservice.utils.onlyNumbersAndLetters
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ValidationExtensionsTest {

    @Test
    fun `the error message list should be empty when the field is not null or blank`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "test"
        ).isNullOrBlank()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should be not empty when the field is null or blank`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "  "
        ).isNullOrBlank()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("must not be empty or null.")
    }

    @Test
    fun `the error message list should be empty when the field is not null`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = 111
        ).isNull()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should be not empty when the field is null`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = null
        ).isNull()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("must not be null.")
    }

    @Test
    fun `the error message list should be empty when the field is a valid CPF`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "548.029.660-40".onlyNumbersAndLetters()
        ).isInvalidCPF()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should not be empty when the field is a invalid CPF`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "000.000.000-01".onlyNumbersAndLetters()
        ).isInvalidCPF()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("the CPF is invalid.")
    }

    @Test
    fun `the error message list should not be empty when the field is a invalid CPF with letters`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "0000000000A"
        ).isInvalidCPF()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("the CPF is invalid.")
    }
}