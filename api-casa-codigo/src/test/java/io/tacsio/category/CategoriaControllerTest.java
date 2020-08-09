package io.tacsio.category;

import com.github.javafaker.Faker;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.tacsio.book.category.dto.CategoriaForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

@QuarkusTest
class CategoriaControllerTest {

	private Faker faker = new Faker();

	@Test
	@DisplayName("Should create new category.")
	void create() {
		String categoryName = faker.gameOfThrones().house();

		RestAssured.given().contentType(ContentType.JSON)
			.when()
			.body(new CategoriaForm(categoryName))
			.post("/categories")
			.then()
			.statusCode(200)
			.body("nome", is(categoryName));
	}

	@Test
	@DisplayName("The name of category should be unique.")
	void categoryNameValidation() {
		String categoryName = faker.gameOfThrones().house();

		CategoriaForm category1 = new CategoriaForm(categoryName);

		RestAssured.given().contentType(ContentType.JSON)
			.when()
			.body(category1)
			.post("/categories")
			.then()
			.statusCode(200)
			.body("nome", is(category1.nome));

		CategoriaForm category2 = new CategoriaForm(categoryName);
		RestAssured.given().contentType(ContentType.JSON)
			.when()
			.body(category2)
			.post("/categories")
			.then()
			.statusCode(400);
	}
}