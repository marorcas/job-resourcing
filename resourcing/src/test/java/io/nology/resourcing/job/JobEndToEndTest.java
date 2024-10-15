package io.nology.resourcing.job;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JobEndToEndTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JobRepository jobRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        jobRepository.deleteAll();

        Job job1 = new Job();
        job1.setName("Job no. 1");
        jobRepository.save(job1);

        Job job2 = new Job();
        job1.setName("Job no. 2");
        jobRepository.save(job2);
    }

    @Test
    public void createJob_success() {
        CreateJobDTO data = new CreateJobDTO();
        data.setName("new job");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/jobs")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("new job"))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }
}
