package com.github.leandrochp.registrationhistoryservice.application.modules

import com.github.leandrochp.registrationhistoryservice.utils.MongoDbMock
import org.koin.dsl.module

val databaseModules = module {
    single {
        MongoDbMock.connect()
    }
}
