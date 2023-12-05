package com.example

import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@MicronautTest(rebuildContext = true)
@Testcontainers
class MicronautRebuildContextErrorSpec extends Specification implements TestPropertyProvider {

    static MSSQLServerContainer mssqlServerContainer = new MSSQLServerContainer().acceptLicense()

    static {
        mssqlServerContainer.start()
    }

    @Inject
    MyEntityRepository myEntityRepository

    @Property(name = "micronaut.server.context-path", value = "/foo")
    void 'connection is closed'() {
        when:
        myEntityRepository.save(new MyEntity())

        then:
        noExceptionThrown()
    }

    @Override
    Map<String, String> getProperties() {
        def properties = ["datasources.default.url": mssqlServerContainer.jdbcUrl,
                          "datasources.default.driverClassName": mssqlServerContainer.driverClassName,
                          "datasources.default.username": mssqlServerContainer.username,
                          "datasources.default.password": mssqlServerContainer.password
        ]
        return properties
    }
}
