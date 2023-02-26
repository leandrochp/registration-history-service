package com.github.leandrochp.registrationhistoryservice.application.web.routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.leandrochp.registrationhistoryservice.application.web.constants.APPLICATION_JSON_CHARSET_UTF_8
import com.github.leandrochp.registrationhistoryservice.application.web.constants.CONTENT_TYPE
import com.github.leandrochp.registrationhistoryservice.application.web.controllers.HealthCheckController
import com.github.leandrochp.registrationhistoryservice.application.web.controllers.RegistrationHistoryController
import com.github.leandrochp.registrationhistoryservice.domain.exceptions.InvalidRequestException
import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RoutesConfig : KoinComponent, LoggableClass() {

    private val objectMapper: ObjectMapper by inject()
    private val healthCheckController: HealthCheckController by inject()
    private val registryHistoryController: RegistrationHistoryController by inject()

    fun getRoutes(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.get("/health-check").handler {
            val response = healthCheckController.health()
            it.response()
                .setStatusCode(HttpResponseStatus.OK.code())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .end(objectMapper.writeValueAsString(response))
        }

        router.run {
            post("/registers").handler {
                val response = try {
                    registryHistoryController.save(objectMapper.readValue(it.body().asString()))
                } catch (ex: MissingKotlinParameterException) {
                    logger.error(ex.localizedMessage).also {
                        throw InvalidRequestException(
                            "Invalid request missing parameter in request.",
                            details = mapOf("error" to listOf(ex.localizedMessage))
                        )
                    }
                }
                it.response()
                    .setStatusCode(HttpResponseStatus.CREATED.code())
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .end(objectMapper.writeValueAsString(response))
            }
            get("/registers").handler {
                val response = registryHistoryController.findAll()
                it.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .end(objectMapper.writeValueAsString(response))
            }
            get("/registers/:id").handler {
                val id = it.pathParam("id")
                val response = registryHistoryController.findById(id)
                it.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .end(objectMapper.writeValueAsString(response))
            }
            get("/registers/first-name/:firstname").handler {
                val firstname = it.pathParam("firstname")
                val response = registryHistoryController.findByFirstName(firstname)

                it.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .end(objectMapper.writeValueAsString(response))
            }
            get("/registers/last-name/:lastname").handler {
                val lastName = it.pathParam("lastName")
                val response = registryHistoryController.findByLastName(lastName)

                it.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                    .end(objectMapper.writeValueAsString(response))
            }
        }

        return router
    }
}
