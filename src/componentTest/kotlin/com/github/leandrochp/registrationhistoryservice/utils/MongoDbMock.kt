package com.github.leandrochp.registrationhistoryservice.utils

import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend

object MongoDbMock : LoggableClass() {

    private var mongoClient: MongoClient? = null

    fun connect(): MongoClient? =
        logger.debug("Connection on MongoDB Memory...").let {
            val mongoServer = MongoServer(MemoryBackend())
            mongoClient = MongoClient(ServerAddress(mongoServer.bind()))
            mongoClient
        }

    fun resetDb() {
        mongoClient?.getDatabase("registration_history_service")?.getCollection("registry")
            ?.deleteMany(BasicDBObject())
    }
}