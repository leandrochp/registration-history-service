package com.github.leandrochp.registrationhistoryservice.application.configs

import com.natpryce.konfig.Configuration
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType
import com.natpryce.konfig.getValue

class EnvironmentVariablesConfig(
    configuration: Configuration = EnvironmentVariables()
) {

    val serverPort = configuration[SERVER_PORT]

    val databaseHost = configuration[DATABASE_HOST]
    val databaseName = configuration[DATABASE_NAME]
    val databaseAuthUser = configuration[DATABASE_AUTH_USER]
    val databaseAuthPassword = configuration[DATABASE_AUTH_PASSWORD]

    companion object {

        private val SERVER_PORT by intType

        private val DATABASE_HOST by stringType
        private val DATABASE_NAME by stringType
        private val DATABASE_AUTH_USER by stringType
        private val DATABASE_AUTH_PASSWORD by stringType
    }

}
