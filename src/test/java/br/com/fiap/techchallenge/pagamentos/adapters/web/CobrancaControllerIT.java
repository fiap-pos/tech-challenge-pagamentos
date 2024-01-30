package br.com.fiap.techchallenge.pagamentos.adapters.web;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasKey;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CobrancaControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void criarCobranca() {
        var cobrancaRequest = getCobrancaRequest();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cobrancaRequest)
                .when()
                .post("/cobrancas")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("pedidoId"))
                .body("$", hasKey("status"))
                .body("$", hasKey("valor"))
                .body("$", hasKey("qrCode"))
                .body("status", equalToIgnoringCase("PENDENTE"))
                .body("valor", equalTo(45.9F))
                .body("qrCode", equalTo("e3BlZGlkb0lkOjEsdmFsb3I6NDUuOX0="));
    }

    @Test
    @Sql(scripts = {"/clean.sql",
            "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void buscarCobrancaPorId() {
        var id = 1L;
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cobrancas/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/cobranca-response.schema.json"));
    }
    @Test
    void buscaCobrancaPorIdInexistente() {
        var id = 2L;
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cobrancas/{id}", id)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
