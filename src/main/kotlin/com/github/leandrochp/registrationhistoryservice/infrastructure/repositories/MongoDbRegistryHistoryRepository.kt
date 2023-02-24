package com.github.leandrochp.registrationhistoryservice.infrastructure.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.leandrochp.registrationhistoryservice.domain.log.LoggableClass
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities.AddressEntity
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities.RegistryHistoryEntity
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.extensions.toModel
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Projections
import io.azam.ulidj.ULID

import org.bson.Document as BsonDocument

class MongoDbRegistryHistoryRepository(
    mongoClient: MongoClient,
    private val objectMapper: ObjectMapper
) : RegistryHistoryRepository, LoggableClass() {

    private val collection: MongoCollection<BsonDocument>

    init {
        val database = mongoClient.getDatabase("registration_history_service")
        collection = database.getCollection("registry")

        logger.debug("Connect in database $database on collection $collection")
    }

    override fun save(registryHistory: RegistryHistory): RegistryHistory {
        val id = ULID.random()

        val registryHistoryEntity = RegistryHistoryEntity(
            id = id,
            firstName = registryHistory.firstName,
            lastName = registryHistory.lastName,
            age = registryHistory.age,
            email = registryHistory.email,
            address = AddressEntity(
                street = registryHistory.address.street,
                city = registryHistory.address.city,
                code = registryHistory.address.code
            )
        )

        logger.debug("Creating a new registry history with the name: ${registryHistoryEntity.firstName}")

        val insertJson = objectMapper.writeValueAsString(registryHistoryEntity)
        val document = BsonDocument.parse(insertJson)
        collection.insertOne(document)

        return registryHistory.copy(id = id).also {
            logger.debug("Registry history has been created with id $id")
        }
    }

    override fun findAll(): List<RegistryHistory> {
        logger.debug("Searching all registry histories")
        return collection.find().projection(Projections.excludeId()).map { document ->
            val jsonResult = document.toJson()
            logger.debug("Has found the follow registry history: $jsonResult")
            objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
        }.toList()
    }

    override fun findById(id: String): RegistryHistory? =
        logger.debug("Searching registry history by id: $id").run {
            BasicDBObject(mutableMapOf("id" to id).toMap())
        }.let {
            collection.find(it).projection(Projections.excludeId()).firstOrNull()?.let { document ->
                val jsonResult = document.toJson()
                logger.debug("Has found the follow registry history with the id: $id [$jsonResult]")
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }
        }

    override fun findByFirstName(firstName: String): List<RegistryHistory> =
        logger.debug("Searching registry history by first name: $firstName").run {
            BasicDBObject(mutableMapOf("first_name" to firstName).toMap())
        }.let {
            collection.find(it).projection(Projections.excludeId()).map { document ->
                val jsonResult = document.toJson()
                logger.debug("Has found the follow registry history with the first name: $firstName [$jsonResult]")
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }.toList()
        }


    override fun findByLastName(lastName: String): List<RegistryHistory> =
        logger.debug("Searching registry history by last name: $lastName").run {
            BasicDBObject(mutableMapOf("last_name" to lastName).toMap())
        }.let {
            collection.find(it).projection(Projections.excludeId()).map { document ->
                val jsonResult = document.toJson()
                logger.debug("Has found the follow registry history with the last name: $lastName [$jsonResult]")
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }.toList()
        }

}
