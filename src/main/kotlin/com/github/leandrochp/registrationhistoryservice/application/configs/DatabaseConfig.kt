package com.github.leandrochp.registrationhistoryservice.application.configs

import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress

object DatabaseConfig : LoggableClass() {

    fun connect(
        host: String,
        database: String,
        userName: String,
        password: String
    ): MongoClient {
        logger.debug("Connection on MongoDB...")

        val serverAddress = ServerAddress(host)
        val credentials = MongoCredential.createCredential(userName, database, password.toCharArray())

        return MongoClient(serverAddress, credentials, MongoClientOptions.builder().build())
    }

}
