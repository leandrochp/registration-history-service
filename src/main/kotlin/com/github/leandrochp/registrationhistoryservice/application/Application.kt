package com.github.leandrochp.registrationhistoryservice.application

import com.github.leandrochp.registrationhistoryservice.application.configs.EnvironmentVariablesConfig
import com.github.leandrochp.registrationhistoryservice.application.modules.loadModules
import com.github.leandrochp.registrationhistoryservice.application.web.handlers.FailureHandlerConfig
import com.github.leandrochp.registrationhistoryservice.application.web.routes.RoutesConfig
import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

object Application : KoinComponent, LoggableClass() {

    private val environmentVariablesConfig: EnvironmentVariablesConfig by inject()
    private val routesConfig: RoutesConfig by inject()
    private val failureHandlerConfig: FailureHandlerConfig by inject()

    fun start() {
        loadModules()
        loadServer()
    }

    private fun loadServer() {
        val options = configureOptions()
        val vertx = Vertx.vertx(options)

        val server = vertx.createHttpServer()
        val port = environmentVariablesConfig.serverPort
        val router = configureRoutes(vertx)

        server.requestHandler(router).listen(port) {
            if (it.succeeded()) {
                logger.info("registration-history-service started on port $port")
            } else {
                logger.error("an error occurred when trie to start registration-history-service: ${it.cause()}")
            }
        }
    }

    private fun configureOptions() = VertxOptions().apply {
        blockedThreadCheckInterval = 5
        blockedThreadCheckIntervalUnit = TimeUnit.SECONDS

        maxEventLoopExecuteTime = 100
        maxEventLoopExecuteTimeUnit = TimeUnit.MILLISECONDS

        maxWorkerExecuteTime = 10
        maxWorkerExecuteTimeUnit = TimeUnit.SECONDS

        warningExceptionTime = 20
        warningExceptionTimeUnit = TimeUnit.SECONDS
    }

    private fun configureRoutes(vertx: Vertx) = routesConfig.getRoutes(vertx)
        .also { failureHandlerConfig.handle(it) }
}

