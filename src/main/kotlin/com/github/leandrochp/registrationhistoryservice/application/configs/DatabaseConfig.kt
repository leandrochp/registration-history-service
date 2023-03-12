package com.github.leandrochp.registrationhistoryservice.application.configs

import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI

object DatabaseConfig : LoggableClass() {

    fun connect(
        host: String,
        port: Int,
        database: String,
        userName: String,
        password: String
    ): MongoClient =
        logger.debug("Connection on MongoDB...").let {
            val mongoClientURI = MongoClientURI(
                "mongodb://$userName:$password@$host:$port/$database?authSource=admin"
            )
            MongoClient(mongoClientURI)
        }

}
