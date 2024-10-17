package io.nology.resourcing.job;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        LocalDate today = LocalDate.now();

        Job job1 = new Job();
        job1.setName("Job 1");
        job1.setStartDate(today);
        job1.setEndDate(today);
        jobRepository.save(job1);

        Job job2 = new Job();
        job2.setName("Job 2");
        job2.setStartDate(today);
        job2.setEndDate(today);
        jobRepository.save(job2);
    }

    @Test
    public void getAllJobs() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        given()
                .when()
                .get("/jobs")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("name", hasItems("Job 1", "Job 2"))
                .body("startDate", hasItems(formattedDate, formattedDate))
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/jobs-schema.json"));
    }

    @Test
    public void createJob_success() {
        CreateJobDTO data = new CreateJobDTO();
        data.setName("new job");
        data.setStartDate(LocalDate.now());
        data.setEndDate(LocalDate.now());

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/jobs")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("new job"))
                .body("startDate", equalTo(formattedDate))
                .body("endDate", equalTo(formattedDate))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }
}
