package com.github.leandrochp.registrationhistoryservice.application.configs

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress

object DatabaseConfig {

    fun connect(
        host: String,
        database: String,
        userName: String,
        password: String
    ): MongoClient {

        val serverAddress = ServerAddress(host)
        val credentials = MongoCredential.createCredential(userName, database, password.toCharArray())

        return MongoClient(serverAddress, credentials, MongoClientOptions.builder().build())
    }

}
