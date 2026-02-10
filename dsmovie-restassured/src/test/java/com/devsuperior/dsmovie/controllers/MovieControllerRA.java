package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Transactional
public class MovieControllerRA {



	private Long existingMovieId, nonExistingMovieId;
	private String adminUsername, adminPassword, clientUsername, clientPassword;
	private String adminToken, clientToken, invalidToken;
	private String empty;

	@BeforeEach
	public void setUp(){
		baseURI = "http://localhost:8080";

		existingMovieId = 1L;
		nonExistingMovieId = 100L;
		adminUsername = "joao@gmail.com";
		adminPassword = "123456";
		clientUsername = "maria@gmail.com";
		clientPassword = "123456";
		empty = "";

		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);

	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		given()
				.when()
				.get("/movies?title=")
				.then()
				.statusCode(200)
				.body("content[0].id",is(1))
				.body("content[0].title", equalTo("The Witcher"))
				.body("content[0].score", is(4.5F))
				.body("content[0].count", is(2))
				.body("content[0].image",equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
