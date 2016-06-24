package org.asion.cloud.server.test;

import org.asion.cloud.server.AsionCloudRegistryServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(
        value = {"server.port=0"},
        webEnvironment = RANDOM_PORT,
        classes = AsionCloudRegistryServerApplication.class
)
public class ApplicationTests {

    @LocalServerPort
    private int port = 0;

    @Test
    public void catalogLoads() {
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://127.0.0.1:" + port + "/eureka/apps", Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void adminLoads() {
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://127.0.0.1:" + port + "/env", Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
