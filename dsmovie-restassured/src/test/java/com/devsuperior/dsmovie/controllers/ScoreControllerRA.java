package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ScoreControllerRA {


    private Long existingMovieId, nonExistingMovieId;
    private String clientUsername, clientPassword, adminUsername, adminPassword;
    private String clientToken, adminToken;
    private Map<String, Object> putScoreInstance;

    @BeforeEach
    public void setUp() {
        baseURI = "http://localhost:8080";

        existingMovieId = 1L;
        nonExistingMovieId = 100L;
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

        clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
        adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);

        putScoreInstance = new HashMap<>();
        putScoreInstance.put("movieId", 1);
        putScoreInstance.put("score", 4);

    }

    @Test
    public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		putScoreInstance.put("movieId", nonExistingMovieId);
        given()
                .header("Authorization", "Bearer " + clientToken)
				.contentType(ContentType.JSON)
				.body(putScoreInstance)
                .when()
                .put("/scores")
                .then()
                .statusCode(404);
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
        putScoreInstance.put("movieId", null);
        given()
                .header("Authorization", "Bearer " + clientToken)
				.contentType(ContentType.JSON)
				.body(putScoreInstance)
                .when()
                .put("/scores")
                .then()
                .statusCode(422)
                .body("errors[0].fieldName", equalTo("movieId"))
				.body("errors[0].message", equalTo("Campo requerido"));
    }

    @Test
    public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		putScoreInstance.put("score", -4);
		given()
				.header("Authorization", "Bearer " + clientToken)
				.contentType(ContentType.JSON)
				.body(putScoreInstance)
				.when()
				.put("/scores")
				.then()
				.statusCode(422)
				.body("errors[0].fieldName", equalTo("score"))
				.body("errors[0].message", equalTo("Valor mínimo 0"));
    }
}
