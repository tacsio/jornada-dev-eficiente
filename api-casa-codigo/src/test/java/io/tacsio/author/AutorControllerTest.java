package io.tacsio.author;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.tacsio.author.dto.AutorForm;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AutorControllerTest {

	private Faker faker = new Faker();

	@Test
	void create() {
		String nome = faker.name().firstName();
		String email = nome + "@mail.com";
		String description = "Some description";
		
		AutorForm form = new AutorForm(nome, email, description);

		given().contentType(ContentType.JSON)
			.when()
			.body(form)
			.post("/authors")
			.then()
			.log().everything()
			.statusCode(200)
			.body("nome", is(nome))
			.body("email", is(email))
			.body("descricao", is(description))
			.body("createdAt", notNullValue());
	}

	@Test
	void shouldNotCreate2AuthorsWithSameEmail() {
		final String email = "dup@mail.com";
		final String description = "Book of Code";

		AutorForm author1 = new AutorForm(faker.name().fullName(), email, description);

		given().contentType(ContentType.JSON)
			.when()
			.body(author1)
			.post("/authors")
			.then()
			.statusCode(200)
			.body("nome", is(author1.nome))
			.body("email", is(author1.email))
			.body("descricao", is(author1.descricao))
			.body("createdAt", notNullValue());

		AutorForm author2 = new AutorForm(faker.name().fullName(), email, description);

		given().contentType(ContentType.JSON)
			.when()
			.body(author2)
			.post("/authors")
			.then()
			.statusCode(400);
	}


}