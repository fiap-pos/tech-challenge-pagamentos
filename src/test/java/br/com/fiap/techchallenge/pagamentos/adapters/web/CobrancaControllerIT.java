//package br.com.fiap.techchallenge.pagamentos.adapters.web;
//
//import br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper;
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobranca;
//import static io.restassured.RestAssured.given;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
//@ActiveProfiles("test")
//@Transactional
//public class CobrancaControllerIT {
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    void setup() {
//        RestAssured.port = port;
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//    }
//
//    @Test
//    void criaUmaNovaCobranca()  {
//        var cobranca = getCobranca();
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(cobranca)
//                .when()
//                .post("/cobrancas")
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.CREATED.value())
//                .body(matchesJsonSchemaInClasspath("schemas/cobranca.schema.json"));
//    }
//     @Test
//    void buscaCobrancaPorId()  {
//        var id = 1L;
//
//        given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/cobrancas/{id}", id)
//                .then()
//                .log().all()
//                .statusCode(HttpStatus.OK.value())
//                .body(matchesJsonSchemaInClasspath("schemas/cobranca.schema.json"));
//    }
//
//}
