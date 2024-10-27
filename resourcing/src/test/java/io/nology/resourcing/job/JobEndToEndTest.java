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

    private LocalDate date;

    private String formattedDate;

    private Long savedJobId;

    private LocalDate newDate;

    private String formattedNewDate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        jobRepository.deleteAll();

        date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formattedDate = date.format(formatter);

        Job job1 = new Job();
        job1.setName("Job 1");
        job1.setStartDate(date);
        job1.setEndDate(date);
        jobRepository.save(job1);
        savedJobId = job1.getId();

        Job job2 = new Job();
        job2.setName("Job 2");
        job2.setStartDate(date);
        job2.setEndDate(date);
        job2.setIsAssigned(true);
        jobRepository.save(job2);

        Job job3 = new Job();
        job3.setName("Job 3");
        job3.setStartDate(date);
        job3.setEndDate(date);
        job3.setIsAssigned(true);
        jobRepository.save(job3);

        Job job4 = new Job();
        job4.setName("Job 4");
        job4.setStartDate(date);
        job4.setEndDate(date);
        jobRepository.save(job4);

        newDate = LocalDate.now().plusDays(1);
        formattedNewDate = newDate.format(formatter);
    }

    @Test
    public void getAllJobs() {
        given()
                .when()
                .get("/jobs")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(4))
                .body("name", hasItems("Job 1", "Job 2", "Job 3", "Job 4"))
                .body("startDate", hasItems(formattedDate, formattedDate, formattedDate, formattedDate))
                .body("endDate", hasItems(formattedDate, formattedDate, formattedDate, formattedDate))
                .body("isAssigned", hasItems(false, true, true, false))
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/jobs-schema.json"));
    }

    @Test
    public void getJobById() {
        given()
                .when()
                .get("/jobs/" + savedJobId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Job 1"))
                .body("startDate", equalTo(formattedDate))
                .body("endDate", equalTo(formattedDate))
                .body("isAssigned", equalTo(false))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }

    @Test
    public void getAllAssignedJobs() {
        given()
                .when()
                .get("/jobs?assigned=true")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("name", hasItems("Job 2", "Job 3"))
                .body("startDate", hasItems(formattedDate, formattedDate))
                .body("endDate", hasItems(formattedDate, formattedDate))
                .body("isAssigned", hasItems(true, true))
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/jobs-schema.json"));
    }

    @Test
    public void getAllNonAssignedJobs() {
        given()
                .when()
                .get("/jobs?assigned=false")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("name", hasItems("Job 1", "Job 4"))
                .body("startDate", hasItems(formattedDate, formattedDate))
                .body("endDate", hasItems(formattedDate, formattedDate))
                .body("isAssigned", hasItems(false, false))
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/jobs-schema.json"));
    }

    @Test
    public void createJob_success() {
        CreateJobDTO data = new CreateJobDTO();
        data.setName("new job");
        data.setStartDate(date);
        data.setEndDate(date);

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
                .body("isAssigned", equalTo(false))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }

    @Test
    public void updateJobName_success() {
        UpdateJobDTO data = new UpdateJobDTO();
        data.setName("updated name test");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/jobs/" + savedJobId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("updated name test"))
                .body("startDate", equalTo(formattedDate))
                .body("endDate", equalTo(formattedDate))
                .body("isAssigned", equalTo(false))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }

    @Test
    public void updateJobDates_success() {
        UpdateJobDTO data = new UpdateJobDTO();
        data.setStartDate(newDate);
        data.setEndDate(newDate);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/jobs/" + savedJobId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Job 1"))
                .body("startDate", equalTo(formattedNewDate))
                .body("endDate", equalTo(formattedNewDate))
                .body("isAssigned", equalTo(false))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }

    @Test
    public void updateJobIsAssigned_success() {
        UpdateJobDTO data = new UpdateJobDTO();
        data.setIsAssigned(true);

        System.out.println(data);

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/jobs/" + savedJobId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Job 1"))
                .body("startDate", equalTo(formattedDate))
                .body("endDate", equalTo(formattedDate))
                .body("isAssigned", equalTo(true))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/job/schemas/job-schema.json"));
    }
}
