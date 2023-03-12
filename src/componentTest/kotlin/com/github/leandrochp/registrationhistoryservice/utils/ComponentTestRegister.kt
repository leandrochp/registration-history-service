package com.github.leandrochp.registrationhistoryservice.utils

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ComponentTestRegister : BeforeAllCallback, BeforeEachCallback, ExtensionContext.Store.CloseableResource {

    override fun beforeAll(context: ExtensionContext?) {
        ComponentTestsEnvironmentSetting.startApp()
    }

    override fun beforeEach(context: ExtensionContext?) {
        ComponentTestsEnvironmentSetting.prepareTest()
    }

    override fun close() {
    }

}
