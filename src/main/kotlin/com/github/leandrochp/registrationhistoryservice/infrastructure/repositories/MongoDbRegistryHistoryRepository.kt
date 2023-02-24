package com.github.leandrochp.registrationhistoryservice.infrastructure.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.leandrochp.registrationhistoryservice.domain.registryhistory.RegistryHistory
import com.github.leandrochp.registrationhistoryservice.domain.repositories.RegistryHistoryRepository
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities.AddressEntity
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.entities.RegistryHistoryEntity
import com.github.leandrochp.registrationhistoryservice.infrastructure.repositories.extensions.toModel
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Projections
import java.util.*
import org.bson.Document as BsonDocument

class MongoDbRegistryHistoryRepository(
    mongoClient: MongoClient,
    private val objectMapper: ObjectMapper
) : RegistryHistoryRepository {

    private val collection: MongoCollection<BsonDocument>

    init {
        val db = mongoClient.getDatabase("registration_history_service")
        collection = db.getCollection("registry")
    }

    override fun save(registryHistory: RegistryHistory): RegistryHistory {
        val id = UUID.randomUUID().toString()

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

        val insertJson = objectMapper.writeValueAsString(registryHistoryEntity)
        val document = BsonDocument.parse(insertJson)

        collection.insertOne(document)

        return registryHistory.copy(id = id)
    }

    override fun findAll(): List<RegistryHistory> {
        return collection.find().projection(Projections.excludeId()).map { document ->
            val result = document.toJson()
            objectMapper.readValue<RegistryHistoryEntity>(result).toModel()
        }.toList()

    }

    override fun findById(id: String): RegistryHistory? {
        return BasicDBObject(mutableMapOf("id" to id).toMap()).let {
            collection.find(it).projection(Projections.excludeId()).firstOrNull()?.let { document ->
                val jsonResult = document.toJson()
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }
        }
    }

    override fun findByFirstName(firstName: String): List<RegistryHistory> {
        return BasicDBObject(mutableMapOf("first_name" to firstName).toMap()).let {
            collection.find(it).projection(Projections.excludeId()).map { document ->
                val jsonResult = document.toJson()
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }.toList()
        }
    }

    override fun findByLastName(lastName: String): List<RegistryHistory> {
        return BasicDBObject(mutableMapOf("last_name" to lastName).toMap()).let {
            collection.find(it).projection(Projections.excludeId()).map { document ->
                val jsonResult = document.toJson()
                objectMapper.readValue<RegistryHistoryEntity>(jsonResult).toModel()
            }.toList()
        }
    }
}
