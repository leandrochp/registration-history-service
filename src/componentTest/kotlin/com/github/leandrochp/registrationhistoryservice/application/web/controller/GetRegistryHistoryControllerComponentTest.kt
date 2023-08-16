package com.github.leandrochp.registrationhistoryservice.application.web.controller

import com.github.leandrochp.registrationhistoryservice.application.config.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.utils.ComponentTestRegister
import com.github.leandrochp.registrationhistoryservice.utils.ComponentTestsUtils
import io.azam.ulidj.ULID
import io.restassured.RestAssured
import io.restassured.config.EncoderConfig
import org.apache.http.HttpStatus
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ComponentTestRegister::class)
class GetRegistryHistoryControllerComponentTest {

    private val env = EnvironmentVariablesConfig()
    private val baseUrl = "http://localhost:${env.serverPort}"

    private val restAssuredConfig = RestAssured.config.encoderConfig(
        EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)
    )

    private val defaultRestAssured = RestAssured.given()
        .header("Content-Type", "application/json")
        .baseUri(baseUrl)
        .config(restAssuredConfig)

    @Test
    fun `should return 404 status code when there were not found registry histories in the database`() {

        defaultRestAssured.`when`().get("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("api_error", IsEqual("REGISTRY_HISTORY_NOT_FOUND"))
            .body("message", IsEqual("There were not found registry histories in the database."))
    }

    @Test
    fun `should return 200 status code when there are found registry histories in the database`() {

        defaultRestAssured.`when`()
            .body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)

        val expectedResult = defaultRestAssured.`when`()
            .get("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .extract().response()

        assertNotNull(expectedResult.body.asString())
    }

    @Test
    fun `should return 404 status code when there is not found registry history ID in the database`() {
        val id = ULID.random()

        defaultRestAssured.`when`().get("/registers/$id")
            .then().assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("api_error", IsEqual("REGISTRY_HISTORY_NOT_FOUND"))
            .body("message", IsEqual("The registry history with the id: [$id] not found."))
    }

    @Test
    fun `should return 200 status code when there is found registry history ID in the database`() {

        val response = defaultRestAssured.`when`()
            .body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .extract().response().jsonPath()


        val expectedResult = defaultRestAssured.`when`().get("/registers/${response.get<String>("id")}")
            .then().assertThat()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .extract().response().jsonPath()

        assertEquals(expectedResult.get("id"), response.get<String>("id"))
    }

    @Test
    fun `should return 404 status code when there were not found registry histories with a first name in the database`() {

        val firstName = "Test"
        defaultRestAssured.`when`().get("/registers/first-name/$firstName")
            .then().assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("api_error", IsEqual("REGISTRY_HISTORY_NOT_FOUND"))
            .body("message", IsEqual("There were not found registry histories with the first name: [$firstName]."))
    }

    @Test
    fun `should return 200 status code when there are found registry histories with a first name in the database`() {

        val response = defaultRestAssured.`when`()
            .body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .extract().response().jsonPath()

        val expectedResult = defaultRestAssured.`when`()
            .get("/registers/first-name/${response.get<String>("first_name")}")
            .then().assertThat()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .extract().response().jsonPath()

        assertEquals(expectedResult.getList<String>("first_name")[0], response.get<String>("first_name"))
    }

    @Test
    fun `should return 404 status code when there were not found registry histories with a last name in the database`() {

        val lastName = "Tset"
        defaultRestAssured.`when`().get("/registers/last-name/$lastName")
            .then().assertThat()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .body("api_error", IsEqual("REGISTRY_HISTORY_NOT_FOUND"))
            .body("message", IsEqual("There were not found registry histories with the last name: [$lastName]."))
    }

    @Test
    fun `should return 200 status code when there are found registry histories with last name in the database`() {

        val response = defaultRestAssured.`when`()
            .body(ComponentTestsUtils.readFile("requests/create_registry_history_request"))
            .post("/registers")
            .then().assertThat()
            .statusCode(HttpStatus.SC_CREATED)
            .and()
            .extract().response().jsonPath()

        val expectedResult = defaultRestAssured.`when`()
            .get("/registers/last-name/${response.get<String>("last_name")}")
            .then().assertThat()
            .statusCode(HttpStatus.SC_OK)
            .and()
            .extract().response().jsonPath()

        assertEquals(expectedResult.getList<String>("last_name")[0], response.get<String>("last_name"))
    }

}