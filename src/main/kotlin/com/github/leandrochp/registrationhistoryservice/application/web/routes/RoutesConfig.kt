package com.github.leandrochp.registrationhistoryservice.application.web.routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.leandrochp.registrationhistoryservice.application.web.constants.APPLICATION_JSON_CHARSET_UTF_8
import com.github.leandrochp.registrationhistoryservice.application.web.constants.CONTENT_TYPE
import com.github.leandrochp.registrationhistoryservice.application.web.controllers.HealthCheckController
import com.github.leandrochp.registrationhistoryservice.application.web.controllers.RegistrationHistoryController
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object RoutesConfig : KoinComponent {

    private val objectMapper: ObjectMapper by inject()
    private val healthCheckController: HealthCheckController by inject()
    private val registryHistoryController: RegistrationHistoryController by inject()

    fun getRoutes(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.route().handler(BodyHandler.create())

        router.get("/health-check").handler {
            val response = healthCheckController.health()
            it.response()
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .end(objectMapper.writeValueAsString(response))
        }

        router.post("/registers").handler {
            val response = registryHistoryController.save(objectMapper.readValue(it.body().asString()))
            it.response()
                .setStatusCode(HttpResponseStatus.CREATED.code())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .end(objectMapper.writeValueAsString(response))
        }

        router.get("/registers/:id").handler {
            val id = it.pathParam("id")
            val response = registryHistoryController.findById(id)
            it.response()
                .setStatusCode(HttpResponseStatus.OK.code())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .end(objectMapper.writeValueAsString(response))
        }

        router.get("/registers").handler {
            val queryParams = it.queryParams()
            val response = when {
                queryParams.contains("firstname") -> {
                    registryHistoryController.findByFirstName(queryParams["firstname"])
                }
                queryParams.contains("lastname") -> {
                    registryHistoryController.findByLastName(queryParams["lastname"])
                }
                else -> registryHistoryController.findAll()
            }

            it.response()
                .setStatusCode(HttpResponseStatus.OK.code())
                .putHeader(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8)
                .end(objectMapper.writeValueAsString(response))
        }

        return router
    }
}
