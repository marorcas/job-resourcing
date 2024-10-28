package io.nology.resourcing.temp;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TempEndToEndTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TempRepository tempRepository;

    private Long savedTempId;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        tempRepository.deleteAll();

        Temp temp1 = new Temp();
        temp1.setFirstName("John");
        temp1.setLastName("Smith");
        tempRepository.save(temp1);
        savedTempId = temp1.getId();

        Temp temp2 = new Temp();
        temp2.setFirstName("Jane");
        temp2.setLastName("Doe");
        tempRepository.save(temp2);
    }

    @Test
    public void getAllTemps() {
        given()
                .when()
                .get("/temps")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("firstName", hasItems("John", "Jane"))
                .body("lastName", hasItems("Smith", "Doe"))
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/temp/schemas/temps-schema.json"));
    }

    @Test
    public void getTempById() {
        given()
                .when()
                .get("/temps/" + savedTempId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Smith"))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/temp/schemas/temp-schema.json"));
    }

    @Test
    public void createTemp_success() {
        CreateTempDTO data = new CreateTempDTO();
        data.setFirstName("New");
        data.setLastName("Temp");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/temps")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("firstName", equalTo("New"))
                .body("lastName", equalTo("Temp"))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/temp/schemas/temp-schema.json"));
    }

    @Test
    public void updateTempName_success() {
        UpdateTempDTO data = new UpdateTempDTO();
        data.setFirstName("Johnny");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .patch("/temps/" + savedTempId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("Johnny"))
                .body("lastName", equalTo("Smith"))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath("io/nology/resourcing/temp/schemas/temp-schema.json"));
    }
}
