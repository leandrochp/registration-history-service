package com.github.leandrochp.registrationhistoryservice.application.web.controllers

import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.utils.ComponentTestRegister
import com.github.leandrochp.registrationhistoryservice.utils.ComponentTestsUtils
import io.restassured.RestAssured
import io.restassured.config.EncoderConfig
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@ExtendWith(ComponentTestRegister::class)
class PostRegistryHistoryControllerComponentTest {

    private val env = EnvironmentVariablesConfig()
    private val baseUrl = "http://localhost:${env.serverPort}"

    private val restAssuredConfig = RestAssured.config.encoderConfig(
        EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)
    )

    private val defaultRest = RestAssured.given()
        .header("Content-Type", "application/json")
        .baseUri(baseUrl)
        .config(restAssuredConfig)

    @Test
    fun `should return 201 status code when the registry history was created`() {

        val response = defaultRest.body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .`when`().post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .extract().response()

        val jsonResponse = response.jsonPath()

        assertThat(jsonResponse.getString("id")).isNotEmpty
        assertThat(jsonResponse.getString("first_name")).isEqualTo("John")
        assertThat(jsonResponse.getString("last_name")).isEqualTo("Wick")
        assertThat(jsonResponse.getString("document")).isEqualTo("277.014.160-05")
        assertThat(jsonResponse.getInt("age")).isEqualTo(44)
        assertThat(jsonResponse.getString("email")).isEqualTo("babayaga@hotelcontinental.com")
        assertThat(jsonResponse.getString("address.street")).isEqualTo("121 Lower Horseshoe, Mill Neck")
        assertThat(jsonResponse.getString("address.city")).isEqualTo("Long Island")
        assertThat(jsonResponse.getString("address.code")).isEqualTo("11765")
    }

    @Test
    fun `should return 400 status code when there is a registry history with the same document already registered`() {

        val response = defaultRest.body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .`when`().post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .extract().response()

        val sameDocResponse = defaultRest.body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .`when`().post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .and()
            .extract().response()

        val expectedResponse =
            ComponentTestsUtils
                .readFile("responses/already_registered_registry_history_response")
                .format(response.jsonPath().get<String>("document"))

        JSONAssert.assertEquals(expectedResponse, sameDocResponse.asString(), JSONCompareMode.LENIENT)
    }

    @Test
    fun `should return 400 status code when there is any invalid field in request`() {

        val response = defaultRest.body(ComponentTestsUtils.readFile("requests/invalid_registry_history_request"))
            .`when`().post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .and()
            .extract().response()

        val expectedResponse = ComponentTestsUtils.readFile("responses/invalid_request_response")
        JSONAssert.assertEquals(expectedResponse, response.asString(), JSONCompareMode.LENIENT)
    }

}
