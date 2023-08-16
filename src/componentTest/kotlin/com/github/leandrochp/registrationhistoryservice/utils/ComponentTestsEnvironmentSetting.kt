package com.github.leandrochp.registrationhistoryservice.utils

import com.github.leandrochp.registrationhistoryservice.application.RegistrationHistoryServiceEntryPoint
import com.github.leandrochp.registrationhistoryservice.application.module.KoinModules
import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import io.vertx.core.http.HttpServer
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object ComponentTestsEnvironmentSetting : LoggableClass() {

    private var applicationAlreadyStarted = false
    private var vertxServer: HttpServer? = null

    fun startApp() {
        ReentrantLock().withLock {
            if (applicationAlreadyStarted.not()) {
                applicationAlreadyStarted = true
                vertxServer = RegistrationHistoryServiceEntryPoint.start()
            }
        }
    }

    fun shutdown() {
        KoinModules.stop()
        vertxServer?.close()
    }

    fun prepareTest() {
        MongoDbMock.resetDb()
    }

}
