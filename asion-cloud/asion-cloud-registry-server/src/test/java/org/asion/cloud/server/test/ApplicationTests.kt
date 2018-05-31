package org.asion.cloud.server.test

import org.asion.cloud.server.AsionCloudRegistryServerApplication
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(
        value = ["server.port=0"],
        webEnvironment = RANDOM_PORT,
        classes = [AsionCloudRegistryServerApplication::class]
)
class ApplicationTests {

    @LocalServerPort
    private val port = 0

    @Test
    fun catalogLoads() {
        val entity = TestRestTemplate().getForEntity(
                "http://127.0.0.1:$port/eureka/apps",
                Map::class.java
        )
        assertEquals(HttpStatus.OK, entity.statusCode)
    }

}
